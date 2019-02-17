package com.tomclaw.nimpas.screen.info.adapter.text

import com.avito.konveyor.blueprint.ItemPresenter

class TextItemPresenter : ItemPresenter<TextItemView, TextItem> {

    override fun bindView(view: TextItemView, item: TextItem, position: Int) {
        view.setHint(item.hint)
        view.setText(item.text)
    }

}