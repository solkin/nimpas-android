package com.tomclaw.nimpas.screen.safe.adapter.blueprint

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.screen.safe.adapter.item.GroupItem
import com.tomclaw.nimpas.screen.safe.adapter.view.GroupItemView
import com.tomclaw.nimpas.screen.safe.adapter.view.GroupItemViewHolder

class GroupItemBlueprint(override val presenter: ItemPresenter<GroupItemView, GroupItem>)
    : ItemBlueprint<GroupItemView, GroupItem> {

    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.group_item,
            creator = { _, view -> GroupItemViewHolder(view) }
    )

    override fun isRelevantItem(item: Item) = item is GroupItem

}