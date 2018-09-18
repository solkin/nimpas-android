package com.tomclaw.nimpas.screen.safe.adapter.pass

import com.avito.konveyor.blueprint.ItemPresenter

class PasswordItemPresenter : ItemPresenter<PasswordItemView, PasswordItem> {

    override fun bindView(view: PasswordItemView, item: PasswordItem, position: Int) {
        view.setIcon(item.id)
        view.setTitle(item.title)
        view.setSubtitle(item.subtitle)
    }

}