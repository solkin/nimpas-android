package com.tomclaw.nimpas.screen.form.plugin

import com.tomclaw.nimpas.screen.form.adapter.FormItem
import com.tomclaw.nimpas.screen.form.adapter.check.CheckItem
import com.tomclaw.nimpas.screen.form.adapter.edit.EditItem
import com.tomclaw.nimpas.storage.Record
import com.tomclaw.nimpas.storage.Shelf
import com.tomclaw.nimpas.templates.Template
import io.reactivex.Completable

class SaveFormPlugin(
        private val groupId: Long,
        private val recordId: Long?,
        private val shelf: Shelf
) : FormPlugin {

    override val action: String = "save"

    override fun operation(
            template: Template,
            items: List<FormItem>
    ): Completable = createRecord(template, items)
            .flatMapCompletable { record ->
                shelf.activeBook().flatMapCompletable { it.addRecord(record) }
            }

    private fun createRecord(
            template: Template,
            items: List<FormItem>
    ) = shelf.activeBook().map { book ->
        val id = recordId ?: book.nextId()
        val time = System.currentTimeMillis()
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
        val record = Record(id, groupId, time, template, fields)
        record
    }

}