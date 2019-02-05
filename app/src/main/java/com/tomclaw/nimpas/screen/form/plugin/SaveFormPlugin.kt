package com.tomclaw.nimpas.screen.form.plugin

import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.journal.Record
import com.tomclaw.nimpas.screen.form.adapter.FormItem
import com.tomclaw.nimpas.screen.form.adapter.check.CheckItem
import com.tomclaw.nimpas.screen.form.adapter.edit.EditItem
import com.tomclaw.nimpas.templates.Template
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class SaveFormPlugin(
        private val groupId: Long,
        private val journal: Journal
) : FormPlugin {

    override val action: String = "save"

    override fun operation(
            template: Template,
            items: List<FormItem>
    ): Completable = Single
            .create<Record> { emitter ->
                val id = journal.nextId()
                val time = System.currentTimeMillis()
                val type = template.type
                        ?: throw IllegalArgumentException("Template type is not specified")
                val fields = items
                        .filter { !it.key.isNullOrEmpty() }
                        .map { item ->
                            val key = item.key!!
                            when (item) {
                                is EditItem -> key to item.text
                                is CheckItem -> key to item.checked.toString()
                                else -> throw IllegalArgumentException("Unsupported item type: ${item.javaClass}")
                            }
                        }
                        .toMap()
                val record = Record(id, groupId, time, type, fields)
                emitter.onSuccess(record)
            }
            .flatMapCompletable { journal.addRecord(it) }

}