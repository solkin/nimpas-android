package com.tomclaw.nimpas.screen.safe

import com.avito.konveyor.blueprint.Item
import com.tomclaw.nimpas.journal.Record
import com.tomclaw.nimpas.screen.safe.adapter.card.CardItem
import com.tomclaw.nimpas.screen.safe.adapter.group.GroupItem
import com.tomclaw.nimpas.screen.safe.adapter.note.NoteItem
import com.tomclaw.nimpas.screen.safe.adapter.pass.PasswordItem
import com.tomclaw.nimpas.templates.TYPE_CARD
import com.tomclaw.nimpas.templates.TYPE_GROUP
import com.tomclaw.nimpas.templates.TYPE_NOTE
import com.tomclaw.nimpas.templates.TYPE_PASSWORD

interface RecordConverter {

    fun convert(record: Record): Item

}

class RecordConverterImpl : RecordConverter {

    override fun convert(record: Record): Item = when (record.template.type) {
        TYPE_GROUP -> GroupItem(record.id, record.getField("title"))
        TYPE_PASSWORD -> PasswordItem(record.id, record.getField("title"), record.getField("username"))
        TYPE_CARD -> CardItem(record.id, record.getField("title"), record.getField("number"))
        TYPE_NOTE -> NoteItem(record.id, record.getField("title"), record.getField("text"))
        else -> throw IllegalArgumentException("Unknown record type!")
    }

    private fun Record.getField(key: String): String {
        return fields[key] ?: throw IllegalArgumentException("Mandatory field '$key' doesn't exist")
    }

}