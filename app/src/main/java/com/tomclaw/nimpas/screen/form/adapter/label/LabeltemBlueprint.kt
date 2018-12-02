package com.tomclaw.nimpas.screen.form.adapter.label

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import com.tomclaw.nimpas.R

class LabelItemBlueprint(
        override val presenter: ItemPresenter<LabelItemView, LabelItem>
) : ItemBlueprint<LabelItemView, LabelItem> {

    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.label_item,
            creator = { _, view -> LabelItemViewHolder(view) }
    )

    override fun isRelevantItem(item: Item) = item is LabelItem

}