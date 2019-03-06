package com.tomclaw.nimpas.screen.safe

import com.avito.konveyor.blueprint.Item
import com.tomclaw.nimpas.storage.Record
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
        TYPE_GROUP -> GroupItem(
                id = record.id,
                title = record.getField("title"),
                icon = record.template.icon
        )
        TYPE_PASSWORD -> PasswordItem(
                id = record.id,
                title = record.getField("title"),
                subtitle = record.getField("username"),
                icon = record.template.icon
        )
        TYPE_CARD -> CardItem(
                id = record.id,
                title = record.getField("title"),
                number = record.getField("number"),
                icon = record.template.icon
        )
        TYPE_NOTE -> NoteItem(
                id = record.id,
                title = record.getField("title"),
                text = record.getField("text"),
                icon = record.template.icon
        )
        else -> throw IllegalArgumentException("Unknown record type!")
    }

    private fun Record.getField(key: String): String {
        return fields[key] ?: throw IllegalArgumentException("Mandatory field '$key' doesn't exist")
    }

}