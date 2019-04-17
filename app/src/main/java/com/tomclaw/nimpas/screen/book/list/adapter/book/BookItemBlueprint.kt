package com.tomclaw.nimpas.screen.book.list.adapter.book

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import com.tomclaw.nimpas.R

class BookItemBlueprint(override val presenter: ItemPresenter<BookItemView, BookItem>)
    : ItemBlueprint<BookItemView, BookItem> {

    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.book_item,
            creator = { _, view -> BookItemViewHolder(view) }
    )

    override fun isRelevantItem(item: Item) = item is BookItem

}