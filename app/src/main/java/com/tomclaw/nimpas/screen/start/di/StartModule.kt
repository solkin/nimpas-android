package com.tomclaw.nimpas.screen.start.di

import android.content.Context
import android.os.Bundle
import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.screen.start.StartInteractor
import com.tomclaw.nimpas.screen.start.StartInteractorImpl
import com.tomclaw.nimpas.screen.start.StartPresenter
import com.tomclaw.nimpas.screen.start.StartPresenterImpl
import com.tomclaw.nimpas.util.PerActivity
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Module
import dagger.Provides

@Module
class StartModule(
        private val context: Context,
        private val state: Bundle?
) {

    @Provides
    @PerActivity
    internal fun providePresenter(
            interactor: StartInteractor,
            schedulers: SchedulersFactory
    ): StartPresenter = StartPresenterImpl(interactor, schedulers, state)

    @Provides
    @PerActivity
    internal fun provideInteractor(
            journal: Journal,
            schedulers: SchedulersFactory
    ): StartInteractor = StartInteractorImpl(journal, schedulers)

}