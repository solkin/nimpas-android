package com.tomclaw.nimpas.screen.safe.adapter.presenter

import com.avito.konveyor.blueprint.ItemPresenter
import com.tomclaw.nimpas.screen.safe.adapter.item.WebItem
import com.tomclaw.nimpas.screen.safe.adapter.view.WebItemView

class WebItemPresenter : ItemPresenter<WebItemView, WebItem> {

    override fun bindView(view: WebItemView, item: WebItem, position: Int) {
        view.setTitle(item.title)
    }

}