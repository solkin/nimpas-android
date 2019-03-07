package com.tomclaw.nimpas.storage

import android.annotation.SuppressLint
import com.tomclaw.crypto.AesCbcWithIntegrity
import com.tomclaw.drawa.util.readNullableInt
import com.tomclaw.drawa.util.readNullableUTF
import com.tomclaw.drawa.util.safeClose
import com.tomclaw.drawa.util.writeNullableInt
import com.tomclaw.drawa.util.writeNullableUTF
import com.tomclaw.nimpas.templates.Field
import com.tomclaw.nimpas.templates.Template
import io.reactivex.Completable
import io.reactivex.Single
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.HashMap


interface Book {

    fun init(keyword: String): Completable

    fun isUnlocked(): Boolean

    fun lock()

    fun unlock(keyword: String): Completable

    fun getRecord(recordId: Long): Single<Record>

    fun getRecords(groupId: Long = GROUP_DEFAULT): Single<List<Record>>

    fun addRecord(record: Record): Completable

    fun deleteRecord(recordId: Long): Completable

    fun nextId(): Long

}

class BookImpl(private val file: File) : Book {

    private var keyword: String? = null
    private var templates: MutableMap<Long, Template>? = null
    private var records: MutableMap<Long, Record>? = null
    private var writeTime: Long = 0
    private var nextId: Long = 0

    override fun init(keyword: String): Completable = Completable.create { emitter ->
        createBook(keyword)
        emitter.onComplete()
    }

    override fun isUnlocked(): Boolean {
        return keyword != null && records != null
    }

    override fun lock() {
        keyword = null
        records = null
    }

    override fun unlock(keyword: String): Completable = Completable.create { emitter ->
        this.keyword = keyword
        readBook(keyword)
        emitter.onComplete()
    }

    override fun getRecord(recordId: Long): Single<Record> = Single.create { emitter ->
        if (isUnlocked()) {
            records?.get(recordId)?.let {
                emitter.onSuccess(it)
                return@create
            }
        }
        emitter.onError(BookLockedException())
    }

    override fun getRecords(groupId: Long): Single<List<Record>> = Single.create { emitter ->
        if (isUnlocked()) {
            val records = records
            if (records != null) {
                records.filterValues {
                    it.groupId == groupId
                }.let {
                    emitter.onSuccess(it.values.toList())
                }
                return@create
            }
        }
        emitter.onError(BookLockedException())
    }

    override fun addRecord(record: Record): Completable = Completable.create { emitter ->
        val keyword = keyword
        val templates = templates
        val records = records
        if (keyword != null && templates != null && records != null) {
            templates[record.template.id] = record.template
            records[record.id] = record
            writeBook(keyword, templates, records)
            emitter.onComplete()
        } else {
            emitter.onError(BookLockedException())
        }
    }

    override fun deleteRecord(recordId: Long): Completable = Completable.create { emitter ->
        val keyword = keyword
        val templates = templates
        val records = records
        if (keyword != null && templates != null && records != null) {
            records.remove(recordId)?.let { record ->
                records.filterValues { it.template.id == record.template.id }
                        .takeIf { it.isNotEmpty() } ?: templates.remove(record.template.id)
                writeBook(keyword, templates, records)
            }
            emitter.onComplete()
        } else {
            emitter.onError(BookLockedException())
        }
    }

    override fun nextId(): Long {
        return ++nextId
    }

    private fun createBook(keyword: String) {
        val templates = mutableMapOf<Long, Template>()
        val records = mutableMapOf<Long, Record>()
        this.templates = templates
        this.records = records
        writeBook(keyword, templates, records)
    }

    private fun writeBook(
            keyword: String,
            templates: Map<Long, Template>,
            records: Map<Long, Record>
    ) {
        var memoryStream: DataOutputStream? = null
        var directStream: DataOutputStream? = null
        try {
            writeTime = System.currentTimeMillis()
            val fileStream = BufferedOutputStream(FileOutputStream(file))
            directStream = DataOutputStream(fileStream)
            with(directStream) {
                writeShort(BOOK_VERSION)
                writeLong(writeTime)
                flush()
            }
            with(directStream) {
                writeInt(templates.size)
                templates.values.forEach { template ->
                    writeLong(template.id)
                    writeInt(template.version)
                    writeNullableInt(template.type)
                    writeNullableUTF(template.title)
                    writeNullableUTF(template.icon)
                    writeNullableUTF(template.color)
                    writeInt(template.fields?.size ?: 0)
                    template.fields?.forEach { field ->
                        writeUTF(field.type)
                        writeNullableUTF(field.key)
                        writeInt(field.params?.size ?: 0)
                        field.params?.forEach { (key, value) ->
                            writeUTF(key)
                            writeUTF(value)
                        }
                    }
                }
                flush()
            }
            val byteStream = ByteArrayOutputStream()
            memoryStream = DataOutputStream(byteStream)
            with(memoryStream) {
                writeLong(nextId)
                writeInt(records.size)
                records.values.forEach { record ->
                    writeLong(record.id)
                    writeLong(record.groupId)
                    writeLong(record.time)
                    writeLong(record.template.id)
                    writeInt(record.fields.size)
                    record.fields.forEach { (key, value) ->
                        writeUTF(key)
                        writeUTF(value)
                    }
                }
                flush()
            }
            val data = byteStream.toByteArray()

            val keys = AesCbcWithIntegrity.generateKeyFromPassword(keyword, SALT)
            val encrypted = AesCbcWithIntegrity.encrypt(data, keys)

            with(directStream) {
                writeInt(encrypted.cipherText.size)
                write(encrypted.cipherText)
                writeInt(encrypted.iv.size)
                write(encrypted.iv)
                writeInt(encrypted.mac.size)
                write(encrypted.mac)
                flush()
            }
        } finally {
            memoryStream.safeClose()
            directStream.safeClose()
        }
    }

    @SuppressLint("UseSparseArrays")
    private fun readBook(keyword: String) {
        if (!file.exists()) {
            createBook(keyword)
        }
        var memoryStream: DataInputStream? = null
        var directStream: DataInputStream? = null
        try {
            val fileStream = BufferedInputStream(FileInputStream(file))
            directStream = DataInputStream(fileStream)
            val version = directStream.readShort()
            val writeTime = directStream.readLong()
            val templates = HashMap<Long, Template>()
            with(directStream) {
                when (version) {
                    VERSION_1 -> {
                        val templatesCount = readInt()
                        for (t in 0 until templatesCount) {
                            val id = readLong()
                            val templateVersion = readInt()
                            val type = readNullableInt()
                            val title = readNullableUTF()
                            val icon = readNullableUTF()
                            val color = readNullableUTF()
                            val fields = mutableListOf<Field>()
                            val fieldsCount = readInt()
                            for (f in 0 until fieldsCount) {
                                val fieldType = readUTF()
                                val fieldKey = readNullableUTF()
                                val paramsCount = readInt()
                                val params = mutableMapOf<String, String>()
                                for (p in 0 until paramsCount) {
                                    val key = readUTF()
                                    val value = readUTF()
                                    params += key to value
                                }
                                fields += Field(fieldType, fieldKey, params)
                            }
                            val template = Template(id, templateVersion, type, title, icon, color, fields)
                            templates += id to template
                        }
                        this@BookImpl.templates = templates
                    }
                    else -> throw UnknownFormatException()
                }
            }
            val encrypted = with(directStream) {
                val cipherText = ByteArray(size = readInt()).apply { readFully(this) }
                val iv = ByteArray(size = readInt()).apply { readFully(this) }
                val mac = ByteArray(size = readInt()).apply { readFully(this) }
                AesCbcWithIntegrity.CipherTextIvMac(cipherText, iv, mac)
            }

            val keys = AesCbcWithIntegrity.generateKeyFromPassword(keyword, SALT)
            val data = AesCbcWithIntegrity.decrypt(encrypted, keys)
            memoryStream = DataInputStream(ByteArrayInputStream(data))
            with(memoryStream) {
                when (version) {
                    VERSION_1 -> {
                        val nextId = readLong()
                        val records = HashMap<Long, Record>()
                        val recordsCount = readInt()
                        for (c in 0 until recordsCount) {
                            val id = readLong()
                            val groupId = readLong()
                            val time = readLong()
                            val templateId = readLong()
                            val fieldsCount = readInt()
                            val fields = mutableMapOf<String, String>()
                            for (i in 0 until fieldsCount) {
                                val key = readUTF()
                                val value = readUTF()
                                fields += key to value
                            }
                            val template = templates[templateId]
                                    ?: throw TemplateNotFoundException()
                            records[id] = Record(id, groupId, time, template, fields)
                        }
                        this@BookImpl.records = records
                        this@BookImpl.writeTime = writeTime
                        this@BookImpl.nextId = nextId
                    }
                    else -> throw UnknownFormatException()
                }
            }
        } finally {
            memoryStream.safeClose()
            directStream.safeClose()
        }
    }

    class UnknownFormatException : Exception()

    class BookLockedException : Exception()

    class TemplateNotFoundException : Exception()

}

private const val BOOK_VERSION = 1

private const val VERSION_1: Short = 1

private const val SALT = "4v9YzypE7CtHzhI/nmrOHxc6qogp8ASaChn7Bc6/VU41wd" +
        "7uZc3fyAeuIInwa9nAwBCZA7Di3FQvSXa1msz7UEwrcZojh8NWGsbNo6Zb73+Os" +
        "4c3TinCVq+8q+95V8/KM0gGy0CTc8HJsfouOGP9GbGyrDD08/rSoY10plYxN8k="
