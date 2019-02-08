package com.tomclaw.nimpas.screen.safe.adapter.card

import com.avito.konveyor.blueprint.ItemPresenter
import com.tomclaw.nimpas.screen.safe.adapter.ItemClickListener

class CardItemPresenter(
        private val listener: ItemClickListener
) : ItemPresenter<CardItemView, CardItem> {

    override fun bindView(view: CardItemView, item: CardItem, position: Int) {
        item.icon?.let { view.setIcon(item.icon, item.id) }
        view.setTitle(item.title)
        view.setNumber(item.number)
        view.setOnClickListener { listener.onItemClick(item) }
    }

}