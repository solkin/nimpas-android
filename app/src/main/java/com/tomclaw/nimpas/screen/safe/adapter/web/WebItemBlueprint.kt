package com.tomclaw.nimpas.screen.safe.adapter.web

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import com.tomclaw.nimpas.R

class WebItemBlueprint(override val presenter: ItemPresenter<WebItemView, WebItem>)
    : ItemBlueprint<WebItemView, WebItem> {

    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.web_item,
            creator = { _, view -> WebItemViewHolder(view) }
    )

    override fun isRelevantItem(item: Item) = item is WebItem

}