package com.tomclaw.nimpas.journal

import android.annotation.SuppressLint
import com.tomclaw.drawa.util.readNullableInt
import com.tomclaw.drawa.util.readNullableUTF
import com.tomclaw.drawa.util.safeClose
import com.tomclaw.drawa.util.writeNullableInt
import com.tomclaw.drawa.util.writeNullableUTF
import io.reactivex.Completable
import io.reactivex.Single
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
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

    fun nextId(): Long

}

class JournalImpl(private val file: File) : Journal {

    private var keyword: String? = null
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
        val records = records
        if (keyword != null && records != null) {
            records[record.id] = record
            writeJournal(keyword, records)
            emitter.onComplete()
        } else {
            emitter.onError(JournalLockedException())
        }
    }

    override fun nextId(): Long {
        return ++nextId
    }

    private fun initJournal(keyword: String) {
        this.records = mutableMapOf<Long, Record>().apply {
            writeJournal(keyword, this)
        }
    }

    private fun writeJournal(keyword: String, records: Map<Long, Record>) {
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
            stream = DataOutputStream(CipherOutputStream(fileStream, cipher))
            with(stream) {
                writeLong(nextId)
                writeInt(records.size)
                records.values.forEach { record ->
                    writeLong(record.id)
                    writeLong(record.groupId)
                    writeLong(record.time)
                    when (record) {
                        is Group -> {
                            writeInt(TYPE_GROUP)
                            record.run {
                                writeUTF(title)
                            }
                        }
                        is Password -> {
                            writeInt(TYPE_PASSWORD)
                            record.run {
                                writeUTF(title)
                                writeNullableUTF(username)
                                writeNullableUTF(password)
                                writeNullableUTF(url)
                                writeNullableUTF(description)
                            }
                        }
                        is Card -> {
                            writeInt(TYPE_CARD)
                            record.run {
                                writeUTF(title)
                                writeUTF(number)
                                writeNullableInt(expiration)
                                writeNullableUTF(holder)
                                writeNullableInt(security)
                            }
                        }
                        is Note -> {
                            writeInt(TYPE_NOTE)
                            record.run {
                                writeUTF(title)
                                writeUTF(text)
                            }
                        }
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
                            val recordType = readInt()
                            val record: Record = when (recordType) {
                                TYPE_GROUP -> {
                                    Group(
                                            id,
                                            groupId,
                                            time,
                                            title = readUTF()
                                    )
                                }
                                TYPE_PASSWORD -> {
                                    Password(
                                            id,
                                            groupId,
                                            time,
                                            title = readUTF(),
                                            username = readNullableUTF(),
                                            password = readNullableUTF(),
                                            url = readNullableUTF(),
                                            description = readNullableUTF()
                                    )
                                }
                                TYPE_CARD -> {
                                    Card(
                                            id,
                                            groupId,
                                            time,
                                            title = readUTF(),
                                            number = readUTF(),
                                            expiration = readNullableInt(),
                                            holder = readNullableUTF(),
                                            security = readNullableInt()
                                    )
                                }
                                TYPE_NOTE -> {
                                    Note(
                                            id,
                                            groupId,
                                            time,
                                            title = readUTF(),
                                            text = readUTF()
                                    )
                                }
                                else -> throw UnknownRecordException()
                            }
                            records[id] = record
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

    class UnknownRecordException : Exception()

    class JournalLockedException : Exception()

}

private const val JOURNAL_VERSION = 1

private const val VERSION_1: Short = 1
