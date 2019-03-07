package com.tomclaw.nimpas.screen.start

import com.tomclaw.nimpas.storage.Book
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Completable

interface StartInteractor {

    fun check(): Completable

}

class StartInteractorImpl(
        private val book: Book,
        private val schedulers: SchedulersFactory
) : StartInteractor {

    override fun check(): Completable {
        return Completable.complete()
                .subscribeOn(schedulers.io())
    }

}