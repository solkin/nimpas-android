package com.tomclaw.nimpas.screen.form

import com.avito.konveyor.blueprint.Item
import com.tomclaw.nimpas.screen.form.adapter.edit.EditItem
import com.tomclaw.nimpas.screen.form.adapter.header.HeaderItem
import com.tomclaw.nimpas.templates.Field

interface FieldConverter {

    fun convert(field: Field): Item

}

class FieldConverterImpl : FieldConverter {

    override fun convert(field: Field): Item {
        // TODO: refactor this
        return when (field.type) {
            FIELD_TYPE_STRING -> EditItem(
                    id = field.hashCode().toLong(),
                    hint = field.params?.get("hint").orEmpty(),
                    text = field.params?.get("text").orEmpty()
            )
            FIELD_TYPE_HEADER -> HeaderItem(
                    id = field.hashCode().toLong(),
                    title = field.params?.get("title").orEmpty()
            )
            else -> throw IllegalArgumentException("Unknown type of field ${field.type}")
        }
    }

}

private const val FIELD_TYPE_STRING = "string"
private const val FIELD_TYPE_HEADER = "header"
