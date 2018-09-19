package com.tomclaw.nimpas.screen.safe

import com.tomclaw.nimpas.journal.Card
import com.tomclaw.nimpas.journal.GROUP_DEFAULT
import com.tomclaw.nimpas.journal.Group
import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.journal.Note
import com.tomclaw.nimpas.journal.Password
import com.tomclaw.nimpas.journal.Record
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
        val records = listOf(
                Group(1, GROUP_DEFAULT, "Group title"),
                Password(2, GROUP_DEFAULT, "Pass Title", "Subtitle", "", "", ""),
                Card(3, GROUP_DEFAULT, "Card Title", "1234 5678 9012 3456", 0, "", 0),
                Note(4, GROUP_DEFAULT, "Note Title", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."),
                Note(5, GROUP_DEFAULT, "Note Title", "Lorem ipsum dolor sit amet."),
                Group(8, 1, "Other title"),
                Note(6, 1, "Some Note", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
                Note(7, 1, "Another Note", "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."),
                Password(9, 8, "Pass Title", "Subtitle", "", "", ""),
                Password(10, 8, "Pass Title", "Subtitle", "", "", "")
        )
        return Observable.just(
                records.filter { it.groupId == groupId }
        )
    }

}