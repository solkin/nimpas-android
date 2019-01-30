package com.tomclaw.nimpas.screen.form.adapter.action

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import com.tomclaw.nimpas.R

class ActionItemBlueprint(
        override val presenter: ItemPresenter<ActionItemView, ActionItem>
) : ItemBlueprint<ActionItemView, ActionItem> {

    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.action_item,
            creator = { _, view -> ActionItemViewHolder(view) }
    )

    override fun isRelevantItem(item: Item) = item is ActionItem

}