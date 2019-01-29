package com.tomclaw.nimpas.screen.form.adapter.button

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import com.tomclaw.nimpas.R

class ButtonItemBlueprint(
        override val presenter: ItemPresenter<ButtonItemView, ButtonItem>
) : ItemBlueprint<ButtonItemView, ButtonItem> {

    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.button_item,
            creator = { _, view -> ButtonItemViewHolder(view) }
    )

    override fun isRelevantItem(item: Item) = item is ButtonItem

}