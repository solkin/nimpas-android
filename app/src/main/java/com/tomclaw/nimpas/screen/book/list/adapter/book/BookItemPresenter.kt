package com.tomclaw.nimpas.screen.book.list.adapter.book

import com.avito.konveyor.blueprint.ItemPresenter
import com.tomclaw.nimpas.screen.book.list.adapter.ItemClickListener

class BookItemPresenter(
        private val listener: ItemClickListener
) : ItemPresenter<BookItemView, BookItem> {

    override fun bindView(view: BookItemView, item: BookItem, position: Int) {
        view.setIcon(item.id)
        view.setTitle(item.title)
        view.setSubtitle(item.subtitle)
        view.setOnClickListener { listener.onItemClick(item) }
    }

}