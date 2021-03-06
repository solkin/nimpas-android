package com.tomclaw.nimpas.screen.safe.adapter.group

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import com.tomclaw.nimpas.R

class GroupItemBlueprint(override val presenter: ItemPresenter<GroupItemView, GroupItem>)
    : ItemBlueprint<GroupItemView, GroupItem> {

    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.group_item,
            creator = { _, view -> GroupItemViewHolder(view) }
    )

    override fun isRelevantItem(item: Item) = item is GroupItem

}