package com.tomclaw.nimpas.screen.safe.adapter.presenter

import com.avito.konveyor.blueprint.ItemPresenter
import com.tomclaw.nimpas.screen.safe.adapter.item.NoteItem
import com.tomclaw.nimpas.screen.safe.adapter.view.NoteItemView

class NoteItemPresenter : ItemPresenter<NoteItemView, NoteItem> {

    override fun bindView(view: NoteItemView, item: NoteItem, position: Int) {
        view.setTitle(item.title)
        view.setText(item.text)
    }

}