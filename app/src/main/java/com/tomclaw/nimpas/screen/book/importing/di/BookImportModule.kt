package com.tomclaw.nimpas.screen.book.importing.di

import android.content.Context
import android.os.Bundle
import com.tomclaw.nimpas.screen.book.importing.BookImportInteractor
import com.tomclaw.nimpas.screen.book.importing.BookImportInteractorImpl
import com.tomclaw.nimpas.screen.book.importing.BookImportPresenter
import com.tomclaw.nimpas.screen.book.importing.BookImportPresenterImpl
import com.tomclaw.nimpas.storage.Shelf
import com.tomclaw.nimpas.util.PerActivity
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Module
import dagger.Provides

@Module
class BookImportModule(
        private val context: Context,
        private val state: Bundle?
) {

    @Provides
    @PerActivity
    internal fun providePresenter(
            interactor: BookImportInteractor,
            schedulers: SchedulersFactory
    ): BookImportPresenter = BookImportPresenterImpl(interactor, schedulers, state)

    @Provides
    @PerActivity
    internal fun provideInteractor(
            shelf: Shelf,
            schedulers: SchedulersFactory
    ): BookImportInteractor = BookImportInteractorImpl(shelf, schedulers)

}