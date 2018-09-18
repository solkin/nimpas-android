package com.tomclaw.nimpas.screen.safe.adapter.note

import com.avito.konveyor.blueprint.ItemPresenter

class NoteItemPresenter : ItemPresenter<NoteItemView, NoteItem> {

    override fun bindView(view: NoteItemView, item: NoteItem, position: Int) {
        view.setTitle(item.title)
        view.setText(item.text)
    }

}