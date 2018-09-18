package com.tomclaw.nimpas.screen.safe.adapter.pass

import com.avito.konveyor.blueprint.ItemPresenter

class PassItemPresenter : ItemPresenter<PassItemView, PassItem> {

    override fun bindView(view: PassItemView, item: PassItem, position: Int) {
        view.setIcon(item.id)
        view.setTitle(item.title)
        view.setSubtitle(item.subtitle)
    }

}