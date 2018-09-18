package com.tomclaw.nimpas.screen.safe.adapter.card

import com.avito.konveyor.blueprint.ItemPresenter

class CardItemPresenter : ItemPresenter<CardItemView, CardItem> {

    override fun bindView(view: CardItemView, item: CardItem, position: Int) {
        view.setImage(item.image)
        view.setTitle(item.title)
        view.setNumber(item.number)
    }

}