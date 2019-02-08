package com.tomclaw.nimpas.screen.safe.adapter.note

import com.avito.konveyor.blueprint.ItemPresenter
import com.tomclaw.nimpas.screen.safe.adapter.ItemClickListener

class NoteItemPresenter(
        private val listener: ItemClickListener
) : ItemPresenter<NoteItemView, NoteItem> {

    override fun bindView(view: NoteItemView, item: NoteItem, position: Int) {
        item.icon?.let { view.setIcon(item.icon, item.id) }
        view.setTitle(item.title)
        view.setText(item.text)
        view.setOnClickListener { listener.onItemClick(item) }
    }

}