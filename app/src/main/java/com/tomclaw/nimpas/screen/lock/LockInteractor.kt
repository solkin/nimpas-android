package com.tomclaw.nimpas.screen.lock

import com.tomclaw.nimpas.storage.Shelf
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Completable

interface LockInteractor {

    fun unlock(keyword: String): Completable

}

class LockInteractorImpl(
        private val shelf: Shelf,
        private val schedulers: SchedulersFactory
) : LockInteractor {

    override fun unlock(keyword: String): Completable {
        return shelf.activeBook()
                .flatMapCompletable { it.unlock(keyword) }
                .subscribeOn(schedulers.io())
    }

}