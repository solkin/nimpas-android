package com.tomclaw.nimpas.screen.start

import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Completable

interface StartInteractor {

    fun check(): Completable

}

class StartInteractorImpl(
        private val journal: Journal,
        private val schedulers: SchedulersFactory
) : StartInteractor {

    override fun check(): Completable {
        return Completable.complete()
                .subscribeOn(schedulers.io())
    }

}