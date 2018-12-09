package com.tomclaw.nimpas.screen.form.adapter.action

import com.avito.konveyor.blueprint.ItemPresenter

class ActionItemPresenter : ItemPresenter<ActionItemView, ActionItem> {

    override fun bindView(view: ActionItemView, item: ActionItem, position: Int) {
        view.setTitle(item.title)
        if (item.icon != null && item.color != null) {
            view.setIcon(item.icon, item.color)
        }
    }

}