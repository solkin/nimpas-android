package com.tomclaw.nimpas.journal

interface Record

data class Group(
        val id: Long,
        val title: String,
        val records: List<Record>
) : Record

data class Password(
        val id: Long,
        val title: String,
        val username: String?,
        val password: String?,
        val url: String?,
        val description: String?
) : Record

data class Card(
        val id: Long,
        val title: String,
        val number: String,
        val expiration: String?,
        val holder: String?,
        val security: String?
) : Record

data class Note(
        val id: Long,
        val title: String,
        val text: String
) : Record