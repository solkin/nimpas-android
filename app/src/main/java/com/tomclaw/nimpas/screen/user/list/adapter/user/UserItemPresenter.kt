package com.tomclaw.nimpas.screen.user.list.adapter.user

import com.avito.konveyor.blueprint.ItemPresenter
import com.tomclaw.nimpas.screen.user.list.adapter.ItemClickListener

class UserItemPresenter(
        private val listener: ItemClickListener
) : ItemPresenter<UserItemView, UserItem> {

    override fun bindView(view: UserItemView, item: UserItem, position: Int) {
        view.setTitle(item.title)
        view.setSubtitle(item.subtitle)
        view.setOnClickListener { listener.onItemClick(item) }
    }

}