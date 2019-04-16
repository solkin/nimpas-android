package com.tomclaw.nimpas.screen.user.list

import com.tomclaw.nimpas.storage.Book
import com.tomclaw.nimpas.storage.Shelf
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Completable
import io.reactivex.Observable

interface UserListInteractor {

    fun listBooks(): Observable<Map<String, Book>>

    fun switchBook(id: String): Completable

}

class UserListInteractorImpl(
        private val shelf: Shelf,
        private val schedulers: SchedulersFactory
) : UserListInteractor {

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