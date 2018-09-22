package com.tomclaw.nimpas.journal

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

interface Journal {

    fun init(keyword: String): Completable

    fun isUnlocked(): Boolean

    fun lock()

    fun unlock(keyword: String): Completable

    fun getRecords(groupId: Long = GROUP_DEFAULT): Single<List<Record>>

    fun addRecord(record: Record): Completable

}

class JournalImpl(private val file: File) : Journal {

    private var keyword: String? = null
    private var records: MutableMap<Long, Record>? = null
    private var writeTime: Long = 0

    override fun init(keyword: String) = Completable.create { emitter ->
        records = mutableMapOf()
        writeJournal()
    }

    override fun isUnlocked(): Boolean {
        return keyword != null && records != null
    }

    override fun lock() {
        keyword = null
        records = null
    }

    override fun unlock(keyword: String) = Completable.create { emitter ->
        this.keyword = keyword
        readJournal()
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
        emitter.onError(JournalIsLockedException())
    }

    override fun addRecord(record: Record): Completable = Completable.create { emitter ->
        val records = records
        if (records != null) {
            records[record.id] = record
            writeJournal()
            emitter.onComplete()
        } else {
            emitter.onError(JournalIsLockedException())
        }
    }

    private fun writeJournal() {
        val records = records ?: throw IllegalArgumentException()
        var stream: DataOutputStream? = null
        try {
            writeTime = System.currentTimeMillis()
            stream = DataOutputStream(BufferedOutputStream(FileOutputStream(file))).apply {
                writeShort(JOURNAL_VERSION)
                writeLong(writeTime)
                // Encrypted data
                writeInt(records.size)
                records.values.forEach { record ->
                    writeLong(record.id)
                    writeLong(record.groupId)
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

    private fun readJournal() {
        val keyword = keyword ?: throw IllegalArgumentException()
        var stream: DataInputStream? = null
        try {
            stream = DataInputStream(BufferedInputStream(FileInputStream(file))).apply {
                val version = readShort()
                when (version) {
                    VERSION_1 -> {
                        val writeTime = readLong()
                        val records = HashMap<Long, Record>()
                        val recordsCount = readInt()
                        for (c in 0..recordsCount) {
                            val id = readLong()
                            val groupId = readLong()
                            val recordType = readInt()
                            val record: Record = when (recordType) {
                                TYPE_GROUP -> {
                                    Group(id, groupId, title = readUTF())
                                }
                                TYPE_PASSWORD -> {
                                    Password(
                                            id,
                                            groupId,
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
                    }
                    else -> throw UnknownFormatException()
                }
            }
        } finally {
            stream.safeClose()
        }
    }

    private class UnknownFormatException : Exception()

    private class UnknownRecordException : Exception()

    private class JournalIsLockedException : Exception()

}

private const val JOURNAL_VERSION = 1

private const val VERSION_1: Short = 1

private const val TYPE_GROUP = 1
private const val TYPE_PASSWORD = 2
private const val TYPE_CARD = 3
private const val TYPE_NOTE = 4