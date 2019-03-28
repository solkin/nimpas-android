package com.tomclaw.nimpas.screen.user.list

import com.tomclaw.nimpas.storage.Book
import com.tomclaw.nimpas.storage.Shelf
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Observable

interface UserListInteractor {

    fun listBooks(): Observable<Map<String, Book>>

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

}