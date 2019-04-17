package com.tomclaw.nimpas.screen.book.list

import com.avito.konveyor.blueprint.Item
import com.tomclaw.nimpas.screen.book.list.adapter.book.BookItem
import com.tomclaw.nimpas.storage.Book

interface BookConverter {

    fun convert(book: Book): Item

}

class BookConverterImpl(
        private val resourceProvider: BookListResourceProvider
) : BookConverter {

    override fun convert(book: Book): Item {
        return BookItem(
                id = book.hashCode().toLong(),
                title = book.getTitle(),
                subtitle = resourceProvider.formatDate(book.getWriteTime())
        )
    }

}