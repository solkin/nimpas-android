package com.tomclaw.nimpas.screen.form.converter

import com.tomclaw.nimpas.screen.form.adapter.FormItem
import com.tomclaw.nimpas.screen.form.adapter.button.ButtonItem
import com.tomclaw.nimpas.screen.form.adapter.check.CheckItem
import com.tomclaw.nimpas.screen.form.adapter.edit.EditItem
import com.tomclaw.nimpas.screen.form.adapter.header.HeaderItem
import com.tomclaw.nimpas.templates.Field

interface FieldConverter {

    fun convert(field: Field): FormItem

}

class FieldConverterImpl : FieldConverter {

    override fun convert(field: Field): FormItem {
        // TODO: refactor this
        return when (field.type) {
            FIELD_TYPE_STRING -> EditItem(
                    id = field.hashCode().toLong(),
                    key = field.key,
                    hint = field.params?.get("hint").orEmpty(),
                    text = field.params?.get("text").orEmpty()
            )
            FIELD_TYPE_HEADER -> HeaderItem(
                    id = field.hashCode().toLong(),
                    key = field.key,
                    title = field.params?.get("title").orEmpty()
            )
            FIELD_TYPE_BUTTON -> ButtonItem(
                    id = field.hashCode().toLong(),
                    key = field.key,
                    action = field.params?.get("action").orEmpty(),
                    title = field.params?.get("title").orEmpty()
            )
            FIELD_TYPE_CHECK -> CheckItem(
                    id = field.hashCode().toLong(),
                    key = field.key,
                    text = field.params?.get("title").orEmpty(),
                    checked = field.params?.get("checked").orEmpty().toBoolean()
            )
            else -> throw IllegalArgumentException("Unknown type of field ${field.type}")
        }
    }

}

private const val FIELD_TYPE_STRING = "string"
private const val FIELD_TYPE_HEADER = "header"
private const val FIELD_TYPE_BUTTON = "button"
private const val FIELD_TYPE_CHECK = "check"
