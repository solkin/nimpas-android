package com.tomclaw.nimpas.screen.safe.adapter.blueprint

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.screen.safe.adapter.item.WebItem
import com.tomclaw.nimpas.screen.safe.adapter.view.WebItemView
import com.tomclaw.nimpas.screen.safe.adapter.view.WebItemViewHolder

class WebItemBlueprint(override val presenter: ItemPresenter<WebItemView, WebItem>)
    : ItemBlueprint<WebItemView, WebItem> {

    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.group_item,
            creator = { _, view -> WebItemViewHolder(view) }
    )

    override fun isRelevantItem(item: Item) = item is WebItem

}