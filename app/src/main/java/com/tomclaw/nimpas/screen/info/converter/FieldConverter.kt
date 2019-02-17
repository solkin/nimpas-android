package com.tomclaw.nimpas.screen.info.converter

import com.tomclaw.nimpas.screen.info.adapter.InfoItem
import com.tomclaw.nimpas.screen.info.adapter.check.CheckItem
import com.tomclaw.nimpas.screen.info.adapter.header.HeaderItem
import com.tomclaw.nimpas.screen.info.adapter.text.TextItem
import com.tomclaw.nimpas.templates.Field

interface FieldConverter {

    fun convert(field: Field, params: Map<String, String>): InfoItem?

}

class FieldConverterImpl : FieldConverter {

    override fun convert(field: Field, params: Map<String, String>): InfoItem? {
        return when (field.type) {
            FIELD_TYPE_STRING -> {
                TextItem(
                        id = field.hashCode().toLong(),
                        key = field.key,
                        text = params[field.key],
                        hint = field.params?.get("hint").orEmpty()
                )
            }
            FIELD_TYPE_HEADER -> HeaderItem(
                    id = field.hashCode().toLong(),
                    key = field.key,
                    title = field.params?.get("title").orEmpty()
            )
            FIELD_TYPE_CHECK -> CheckItem(
                    id = field.hashCode().toLong(),
                    key = field.key,
                    text = field.params?.get("title").orEmpty(),
                    checked = params[field.key].orEmpty().toBoolean()
            )
            else -> null
        }
    }

}

private const val FIELD_TYPE_STRING = "string"
private const val FIELD_TYPE_HEADER = "header"
private const val FIELD_TYPE_CHECK = "check"
