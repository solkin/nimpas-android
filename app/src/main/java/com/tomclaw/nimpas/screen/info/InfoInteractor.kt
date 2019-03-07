package com.tomclaw.nimpas.screen.info

import com.tomclaw.nimpas.storage.Book
import com.tomclaw.nimpas.storage.Record
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Completable
import io.reactivex.Single

interface InfoInteractor {

    fun getRecord(recordId: Long): Single<Record>

    fun deleteRecord(recordId: Long): Completable

    fun addRecord(record: Record): Completable

}

class InfoInteractorImpl(
        private val book: Book,
        private val schedulers: SchedulersFactory
) : InfoInteractor {

    override fun getRecord(recordId: Long): Single<Record> {
        return book.getRecord(recordId)
                .subscribeOn(schedulers.io())
    }

    override fun deleteRecord(recordId: Long): Completable {
        return book.deleteRecord(recordId)
                .subscribeOn(schedulers.io())
    }

    override fun addRecord(record: Record): Completable {
        return book.addRecord(record)
                .subscribeOn(schedulers.io())
    }

}