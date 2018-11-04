package com.tomclaw.nimpas.screen.form.adapter.input

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import com.tomclaw.nimpas.R

class InputItemBlueprint(override val presenter: ItemPresenter<InputItemView, InputItem>)
    : ItemBlueprint<InputItemView, InputItem> {

    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.input_item,
            creator = { _, view -> InputItemViewHolder(view) }
    )

    override fun isRelevantItem(item: Item) = item is InputItem

}