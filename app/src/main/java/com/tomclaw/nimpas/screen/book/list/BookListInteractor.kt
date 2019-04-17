package com.tomclaw.nimpas.screen.book.list

import com.tomclaw.nimpas.storage.Book
import com.tomclaw.nimpas.storage.Shelf
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Completable
import io.reactivex.Observable

interface BookListInteractor {

    fun listBooks(): Observable<Map<String, Book>>

    fun switchBook(id: String): Completable

}

class BookListInteractorImpl(
        private val shelf: Shelf,
        private val schedulers: SchedulersFactory
) : BookListInteractor {

    override fun listBooks(): Observable<Map<String, Book>> {
        return shelf.listBooks()
                .toObservable()
                .subscribeOn(schedulers.io())
    }

    override fun switchBook(id: String): Completable {
        return shelf.switchBook(id)
                .subscribeOn(schedulers.io())
    }

}