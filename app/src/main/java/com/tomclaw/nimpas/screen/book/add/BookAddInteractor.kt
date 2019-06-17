package com.tomclaw.nimpas.screen.book.add

import com.tomclaw.nimpas.storage.Shelf
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Completable

interface BookAddInteractor {

    fun createBook(title: String, keyword: String): Completable

}

class BookAddInteractorImpl(
        private val shelf: Shelf,
        private val schedulers: SchedulersFactory
) : BookAddInteractor {

    override fun createBook(title: String, keyword: String): Completable {
        return shelf.createBook()
                .flatMapCompletable { shelf.switchBook(it) }
                .subscribeOn(schedulers.io())
    }

}