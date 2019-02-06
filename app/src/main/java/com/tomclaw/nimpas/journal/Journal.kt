package com.tomclaw.nimpas.journal

import android.annotation.SuppressLint
import com.tomclaw.drawa.util.*
import com.tomclaw.nimpas.templates.Field
import com.tomclaw.nimpas.templates.Template
import io.reactivex.Completable
import io.reactivex.Single
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.IllegalStateException
import java.util.HashMap
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.spec.SecretKeySpec


interface Journal {

    fun init(keyword: String): Completable

    fun isUnlocked(): Boolean

    fun lock()

    fun unlock(keyword: String): Completable

    fun getRecords(groupId: Long = GROUP_DEFAULT): Single<List<Record>>

    fun addRecord(record: Record): Completable

    fun deleteRecord(recordId: Long): Completable

    fun nextId(): Long

}

class JournalImpl(private val file: File) : Journal {

    private var keyword: String? = null
    private var templates: MutableMap<Long, Template>? = null
    private var records: MutableMap<Long, Record>? = null
    private var writeTime: Long = 0
    private var nextId: Long = 0

    override fun init(keyword: String) = Completable.create { emitter ->
        initJournal(keyword)
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
        readJournal(keyword)
        emitter.onComplete()
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
        emitter.onError(JournalLockedException())
    }

    override fun addRecord(record: Record): Completable = Completable.create { emitter ->
        val keyword = keyword
        val templates = templates
        val records = records
        if (keyword != null && templates != null && records != null) {
            templates[record.template.id] = record.template
            records[record.id] = record
            writeJournal(keyword, templates, records)
            emitter.onComplete()
        } else {
            emitter.onError(JournalLockedException())
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
                writeJournal(keyword, templates, records)
            }
            emitter.onComplete()
        } else {
            emitter.onError(JournalLockedException())
        }
    }

    override fun nextId(): Long {
        return ++nextId
    }

    private fun initJournal(keyword: String) {
        val templates = mutableMapOf<Long, Template>()
        val records = mutableMapOf<Long, Record>()
        this.templates = templates
        this.records = records
        writeJournal(keyword, templates, records)
    }

    private fun writeJournal(
            keyword: String,
            templates: Map<Long, Template>,
            records: Map<Long, Record>
    ) {
        var stream: DataOutputStream? = null
        try {
            writeTime = System.currentTimeMillis()
            val cipher = Cipher.getInstance("Blowfish").apply {
                val key = SecretKeySpec(keyword.toByteArray(), "Blowfish")
                init(Cipher.ENCRYPT_MODE, key)
            }
            val fileStream = BufferedOutputStream(FileOutputStream(file))
            stream = DataOutputStream(fileStream)
            with(stream) {
                writeShort(JOURNAL_VERSION)
                writeLong(writeTime)
                flush()
            }
            with(stream) {
                writeInt(templates.size)
                templates.values.forEach { template ->
                    writeLong(template.id)
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
            stream = DataOutputStream(CipherOutputStream(fileStream, cipher))
            with(stream) {
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
        } finally {
            stream.safeClose()
        }
    }

    @SuppressLint("UseSparseArrays")
    private fun readJournal(keyword: String) {
        if (!file.exists()) {
            initJournal(keyword)
        }
        var stream: DataInputStream? = null
        try {
            val cipher = Cipher.getInstance("Blowfish").apply {
                val key = SecretKeySpec(keyword.toByteArray(), "Blowfish")
                init(Cipher.DECRYPT_MODE, key)
            }
            val fileStream = BufferedInputStream(FileInputStream(file))
            stream = DataInputStream(fileStream)
            val version = stream.readShort()
            val writeTime = stream.readLong()
            val templates = HashMap<Long, Template>()
            with(stream) {
                when (version) {
                    VERSION_1 -> {
                        val templatesCount = readInt()
                        for (t in 0 until templatesCount) {
                            val id = readLong()
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
                            val template = Template(id, type, title, icon, color, fields)
                            templates += id to template
                        }
                        this@JournalImpl.templates = templates
                    }
                    else -> throw UnknownFormatException()
                }
            }
            stream = DataInputStream(CipherInputStream(fileStream, cipher))
            with(stream) {
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
                        this@JournalImpl.records = records
                        this@JournalImpl.writeTime = writeTime
                        this@JournalImpl.nextId = nextId
                    }
                    else -> throw UnknownFormatException()
                }
            }
        } finally {
            stream.safeClose()
        }
    }

    class UnknownFormatException : Exception()

    class JournalLockedException : Exception()

    class TemplateNotFoundException : Exception()

}

private const val JOURNAL_VERSION = 1

private const val VERSION_1: Short = 1
