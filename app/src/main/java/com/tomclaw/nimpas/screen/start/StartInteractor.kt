package com.tomclaw.nimpas.screen.start

import com.tomclaw.nimpas.storage.Shelf
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Completable

interface StartInteractor {

    fun check(): Completable

}

class StartInteractorImpl(
        private val shelf: Shelf,
        private val schedulers: SchedulersFactory
) : StartInteractor {

    override fun check(): Completable {
        return shelf.activeBook()
                .onErrorResumeNext {
                    shelf.createBook().flatMap { shelf.switchBook(it).andThen(shelf.activeBook()) }
                }
                .flatMapCompletable {
                    when {
                        it.isUnlocked() -> Completable.complete()
                        else -> Completable.error(Exception("Book is locked"))
                    }
                }
                .subscribeOn(schedulers.io())
    }

}