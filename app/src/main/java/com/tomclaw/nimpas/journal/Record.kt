package com.tomclaw.nimpas.journal

abstract class Record(
        val id: Long,
        val groupId: Long,
        val time: Long
)

class Group(
        id: Long,
        groupId: Long,
        time: Long,
        val title: String
) : Record(id, groupId, time)

class Password(
        id: Long,
        groupId: Long,
        time: Long,
        val title: String,
        val username: String?,
        val password: String?,
        val url: String?,
        val description: String?
) : Record(id, groupId, time)

class Card(
        id: Long,
        groupId: Long,
        time: Long,
        val title: String,
        val number: String,
        val expiration: Int?,
        val holder: String?,
        val security: Int?
) : Record(id, groupId, time)

class Note(
        id: Long,
        groupId: Long,
        time: Long,
        val title: String,
        val text: String
) : Record(id, groupId, time)

const val GROUP_DEFAULT = 0L