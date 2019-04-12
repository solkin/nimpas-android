package com.tomclaw.nimpas.screen.user.list

import com.avito.konveyor.blueprint.Item
import com.tomclaw.nimpas.screen.user.list.adapter.user.UserItem
import com.tomclaw.nimpas.storage.Book

interface BookConverter {

    fun convert(book: Book): Item

}

class BookConverterImpl(
        private val resourceProvider: UserListResourceProvider
) : BookConverter {

    override fun convert(book: Book): Item {
        return UserItem(
                id = book.hashCode().toLong(),
                title = book.getTitle(),
                subtitle = resourceProvider.formatDate(book.getWriteTime())
        )
    }

}