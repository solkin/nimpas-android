package com.tomclaw.nimpas.screen.info

import com.tomclaw.nimpas.storage.Journal
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
        private val journal: Journal,
        private val schedulers: SchedulersFactory
) : InfoInteractor {

    override fun getRecord(recordId: Long): Single<Record> {
        return journal.getRecord(recordId)
                .subscribeOn(schedulers.io())
    }

    override fun deleteRecord(recordId: Long): Completable {
        return journal.deleteRecord(recordId)
                .subscribeOn(schedulers.io())
    }

    override fun addRecord(record: Record): Completable {
        return journal.addRecord(record)
                .subscribeOn(schedulers.io())
    }

}