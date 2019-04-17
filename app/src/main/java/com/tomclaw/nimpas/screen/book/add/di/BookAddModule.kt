package com.tomclaw.nimpas.screen.book.add.di

import android.content.Context
import android.os.Bundle
import com.tomclaw.nimpas.screen.book.add.BookAddInteractor
import com.tomclaw.nimpas.screen.book.add.BookAddInteractorImpl
import com.tomclaw.nimpas.screen.book.add.BookAddPresenter
import com.tomclaw.nimpas.screen.book.add.BookAddPresenterImpl
import com.tomclaw.nimpas.storage.Shelf
import com.tomclaw.nimpas.util.PerActivity
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Module
import dagger.Provides

@Module
class BookAddModule(
        private val context: Context,
        private val state: Bundle?
) {

    @Provides
    @PerActivity
    internal fun providePresenter(
            interactor: BookAddInteractor,
            schedulers: SchedulersFactory
    ): BookAddPresenter = BookAddPresenterImpl(interactor, schedulers, state)

    @Provides
    @PerActivity
    internal fun provideInteractor(
            shelf: Shelf,
            schedulers: SchedulersFactory
    ): BookAddInteractor = BookAddInteractorImpl(shelf, schedulers)

}