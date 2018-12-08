package com.tomclaw.nimpas.journal

data class Record(
        val id: Long,
        val groupId: Long,
        val time: Long,
        val type: Int,
        val fields: Map<String, String>
)

const val GROUP_DEFAULT = 0L

const val TYPE_GROUP = 1
const val TYPE_PASSWORD = 2
const val TYPE_CARD = 3
const val TYPE_NOTE = 4
