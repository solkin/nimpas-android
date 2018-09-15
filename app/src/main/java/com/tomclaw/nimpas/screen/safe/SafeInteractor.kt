package com.tomclaw.nimpas.screen.safe

import android.database.Observable
import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.journal.Record
import com.tomclaw.nimpas.util.SchedulersFactory

interface SafeInteractor {

    fun getRecords(): Observable<Record>

    fun getRecords(groupId: Int): Observable<Record>

}

class SafeInteractorImpl(private val journal: Journal,
                         private val schedulers: SchedulersFactory) : SafeInteractor {

    override fun getRecords(): Observable<Record> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRecords(groupId: Int): Observable<Record> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}