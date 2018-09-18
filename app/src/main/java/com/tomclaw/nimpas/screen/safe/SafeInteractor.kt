package com.tomclaw.nimpas.screen.safe

import com.tomclaw.nimpas.journal.Card
import com.tomclaw.nimpas.journal.Group
import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.journal.Note
import com.tomclaw.nimpas.journal.Password
import com.tomclaw.nimpas.journal.Record
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Observable

interface SafeInteractor {

    fun getRecords(): Observable<List<Record>>

    fun getRecords(groupId: Int): Observable<List<Record>>

}

class SafeInteractorImpl(private val journal: Journal,
                         private val schedulers: SchedulersFactory) : SafeInteractor {

    override fun getRecords(): Observable<List<Record>> {
        return Observable.just(listOf(
                Group(1, "Group title", emptyList()),
                Password(2, "Pass Title", "Subtitle", "", "", ""),
                Card(3, "Card Title", "1234 5678 9012 3456", 0, "", 0),
                Note(4, "Note Title", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."),
                Note(5, "Note Title", "Lorem ipsum dolor sit amet.")
        ))
    }

    override fun getRecords(groupId: Int): Observable<List<Record>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}