package com.tomclaw.nimpas.screen.user.list

import com.avito.konveyor.blueprint.Item
import com.tomclaw.nimpas.storage.Book

interface BookConverter {

    fun convert(book: Book): Item

}

class BookConverterImpl : BookConverter {

    override fun convert(book: Book): Item {
        TODO("not implemented")
    }

}