package com.tomclaw.nimpas.screen.safe

import com.tomclaw.nimpas.storage.GROUP_DEFAULT
import com.tomclaw.nimpas.storage.Journal
import com.tomclaw.nimpas.storage.Record
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Observable

interface SafeInteractor {

    fun getRecords(groupId: Long = GROUP_DEFAULT): Observable<List<Record>>

}

class SafeInteractorImpl(
        private val journal: Journal,
        private val schedulers: SchedulersFactory
) : SafeInteractor {

    override fun getRecords(groupId: Long): Observable<List<Record>> {
        return journal.getRecords(groupId)
                .toObservable()
                .subscribeOn(schedulers.io())
    }

}