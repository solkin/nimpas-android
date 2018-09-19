package com.tomclaw.nimpas.journal

abstract class Record(
        val id: Long,
        val groupId: Long
)

class Group(
        id: Long,
        groupId: Long,
        val title: String
) : Record(id, groupId)

class Password(
        id: Long,
        groupId: Long,
        val title: String,
        val username: String?,
        val password: String?,
        val url: String?,
        val description: String?
) : Record(id, groupId)

class Card(
        id: Long,
        groupId: Long,
        val title: String,
        val number: String,
        val expiration: Int?,
        val holder: String?,
        val security: Int?
) : Record(id, groupId)

class Note(
        id: Long,
        groupId: Long,
        val title: String,
        val text: String
) : Record(id, groupId)

const val GROUP_DEFAULT = 0L