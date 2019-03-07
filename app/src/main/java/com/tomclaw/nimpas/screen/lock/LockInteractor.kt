package com.tomclaw.nimpas.screen.lock

import com.tomclaw.nimpas.storage.Book
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Completable

interface LockInteractor {

    fun unlock(keyword: String): Completable

}

class LockInteractorImpl(
        private val book: Book,
        private val schedulers: SchedulersFactory
) : LockInteractor {

    override fun unlock(keyword: String): Completable {
        return book.unlock(keyword)
                .subscribeOn(schedulers.io())
    }

}