package com.tomclaw.nimpas.screen.safe.adapter.pass

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import com.tomclaw.nimpas.R

class PassItemBlueprint(override val presenter: ItemPresenter<PassItemView, PassItem>)
    : ItemBlueprint<PassItemView, PassItem> {

    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.pass_item,
            creator = { _, view -> PassItemViewHolder(view) }
    )

    override fun isRelevantItem(item: Item) = item is PassItem

}