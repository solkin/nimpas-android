package com.tomclaw.nimpas.screen.info

import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.journal.Record
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Single

interface InfoInteractor {

    fun getRecord(recordId: Long): Single<Record>

}

class InfoInteractorImpl(
        private val journal: Journal,
        private val schedulers: SchedulersFactory
) : InfoInteractor {

    override fun getRecord(recordId: Long): Single<Record> {
        return journal.getRecord(recordId)
    }

}