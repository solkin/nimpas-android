package com.tomclaw.nimpas.screen.safe.adapter.web

import com.avito.konveyor.blueprint.ItemPresenter

class WebItemPresenter : ItemPresenter<WebItemView, WebItem> {

    override fun bindView(view: WebItemView, item: WebItem, position: Int) {
        view.setImage(item.image)
        view.setTitle(item.title)
        view.setSubtitle(item.subtitle)
    }

}