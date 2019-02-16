package com.tomclaw.nimpas.screen.form.adapter.header

import com.avito.konveyor.blueprint.ItemPresenter

class HeaderItemPresenter : ItemPresenter<HeaderItemView, HeaderItem> {

    override fun bindView(view: HeaderItemView, item: HeaderItem, position: Int) {
        view.setTitle(item.title)
    }

}