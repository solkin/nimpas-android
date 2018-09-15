package com.tomclaw.nimpas.screen.safe.adapter.presenter

import com.avito.konveyor.blueprint.ItemPresenter
import com.tomclaw.nimpas.screen.safe.adapter.item.GroupItem
import com.tomclaw.nimpas.screen.safe.adapter.view.GroupItemView

class GroupItemPresenter : ItemPresenter<GroupItemView, GroupItem> {

    override fun bindView(view: GroupItemView, item: GroupItem, position: Int) {
        view.setTitle(item.title)
    }

}