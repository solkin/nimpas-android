package com.tomclaw.nimpas.screen.safe.adapter.group

import com.avito.konveyor.blueprint.ItemPresenter
import com.tomclaw.nimpas.screen.safe.adapter.ItemClickListener

class GroupItemPresenter(private val listener: ItemClickListener) : ItemPresenter<GroupItemView, GroupItem> {

    override fun bindView(view: GroupItemView, item: GroupItem, position: Int) {
        view.setIcon(item.id)
        view.setTitle(item.title)
        view.setOnClickListener { listener.onItemClick(item) }
    }

}