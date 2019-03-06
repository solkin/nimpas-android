package com.tomclaw.nimpas.screen.lock

import com.tomclaw.nimpas.storage.Journal
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Completable

interface LockInteractor {

    fun unlock(keyword: String): Completable

}

class LockInteractorImpl(
        private val journal: Journal,
        private val schedulers: SchedulersFactory
) : LockInteractor {

    override fun unlock(keyword: String): Completable {
        return journal.unlock(keyword)
                .subscribeOn(schedulers.io())
    }

}