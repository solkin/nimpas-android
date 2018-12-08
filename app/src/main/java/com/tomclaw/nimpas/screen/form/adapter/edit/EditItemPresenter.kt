package com.tomclaw.nimpas.screen.form.adapter.edit

import com.avito.konveyor.blueprint.ItemPresenter

class EditItemPresenter : ItemPresenter<EditItemView, EditItem> {

    override fun bindView(view: EditItemView, item: EditItem, position: Int) {
        view.setHint(item.hint)
        view.setText(item.text)
    }

}