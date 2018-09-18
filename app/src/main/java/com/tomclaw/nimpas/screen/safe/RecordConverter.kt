package com.tomclaw.nimpas.screen.safe

import com.avito.konveyor.blueprint.Item
import com.tomclaw.nimpas.journal.Card
import com.tomclaw.nimpas.journal.Group
import com.tomclaw.nimpas.journal.Note
import com.tomclaw.nimpas.journal.Password
import com.tomclaw.nimpas.journal.Record
import com.tomclaw.nimpas.screen.safe.adapter.card.CardItem
import com.tomclaw.nimpas.screen.safe.adapter.group.GroupItem
import com.tomclaw.nimpas.screen.safe.adapter.note.NoteItem
import com.tomclaw.nimpas.screen.safe.adapter.pass.PasswordItem

interface RecordConverter {

    fun convert(record: Record): Item

}

class RecordConverterImpl : RecordConverter {

    override fun convert(record: Record): Item {
        return when (record) {
            is Group -> GroupItem(record.id, record.title)
            is Password -> PasswordItem(record.id, record.title, record.username)
            is Card -> CardItem(record.id, record.title, record.number)
            is Note -> NoteItem(record.id, record.title, record.text)
            else -> throw IllegalArgumentException("Unknown record type!")
        }
    }

}