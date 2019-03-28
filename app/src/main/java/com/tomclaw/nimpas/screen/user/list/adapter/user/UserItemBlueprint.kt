package com.tomclaw.nimpas.screen.user.list.adapter.user

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import com.tomclaw.nimpas.R

class UserItemBlueprint(override val presenter: ItemPresenter<UserItemView, UserItem>)
    : ItemBlueprint<UserItemView, UserItem> {

    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.user_item,
            creator = { _, view -> UserItemViewHolder(view) }
    )

    override fun isRelevantItem(item: Item) = item is UserItem

}