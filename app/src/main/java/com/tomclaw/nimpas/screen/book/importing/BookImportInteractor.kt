package com.tomclaw.nimpas.screen.book.importing

import android.net.Uri
import com.tomclaw.nimpas.storage.Shelf
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Completable

interface BookImportInteractor {

    fun importBook(uri: Uri): Completable

}

class BookImportInteractorImpl(
        private val shelf: Shelf,
        private val schedulers: SchedulersFactory
) : BookImportInteractor {

    override fun importBook(uri: Uri): Completable {
        return shelf.importBook(uri)
                .flatMapCompletable { shelf.switchBook(it) }
                .subscribeOn(schedulers.io())
    }

}