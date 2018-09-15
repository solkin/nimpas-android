package com.tomclaw.nimpas.screen.safe.adapter.blueprint

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.screen.safe.adapter.item.CardItem
import com.tomclaw.nimpas.screen.safe.adapter.view.CardItemView
import com.tomclaw.nimpas.screen.safe.adapter.view.CardItemViewHolder

class CardItemBlueprint(override val presenter: ItemPresenter<CardItemView, CardItem>)
    : ItemBlueprint<CardItemView, CardItem> {

    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.card_item,
            creator = { _, view -> CardItemViewHolder(view) }
    )

    override fun isRelevantItem(item: Item) = item is CardItem

}