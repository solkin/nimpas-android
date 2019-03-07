package com.tomclaw.nimpas.screen.form.plugin

import com.tomclaw.nimpas.storage.Book
import com.tomclaw.nimpas.storage.Record
import com.tomclaw.nimpas.screen.form.adapter.FormItem
import com.tomclaw.nimpas.screen.form.adapter.check.CheckItem
import com.tomclaw.nimpas.screen.form.adapter.edit.EditItem
import com.tomclaw.nimpas.templates.Template
import io.reactivex.Completable
import io.reactivex.Single

class SaveFormPlugin(
        private val groupId: Long,
        private val recordId: Long?,
        private val book: Book
) : FormPlugin {

    override val action: String = "save"

    override fun operation(
            template: Template,
            items: List<FormItem>
    ): Completable = createRecord(template, items).flatMapCompletable { book.addRecord(it) }

    private fun createRecord(
            template: Template,
            items: List<FormItem>
    ) = Single.create<Record> { emitter ->
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
        emitter.onSuccess(record)
    }

}