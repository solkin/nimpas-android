package com.tomclaw.nimpas.screen.safe.adapter.presenter

import com.avito.konveyor.blueprint.ItemPresenter
import com.tomclaw.nimpas.screen.safe.adapter.item.CardItem
import com.tomclaw.nimpas.screen.safe.adapter.view.CardItemView

class CardItemPresenter : ItemPresenter<CardItemView, CardItem> {

    override fun bindView(view: CardItemView, item: CardItem, position: Int) {
        view.setImage(item.image)
        view.setTitle(item.title)
        view.setNumber(item.number)
    }

}