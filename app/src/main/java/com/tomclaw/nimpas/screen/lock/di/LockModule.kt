package com.tomclaw.nimpas.screen.lock.di

import android.content.Context
import android.os.Bundle
import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.screen.lock.LockInteractor
import com.tomclaw.nimpas.screen.lock.LockInteractorImpl
import com.tomclaw.nimpas.screen.lock.LockPresenter
import com.tomclaw.nimpas.screen.lock.LockPresenterImpl
import com.tomclaw.nimpas.util.PerActivity
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Module
import dagger.Provides

@Module
class LockModule(
        private val context: Context,
        private val state: Bundle?
) {

    @Provides
    @PerActivity
    internal fun providePresenter(
            interactor: LockInteractor,
            schedulers: SchedulersFactory
    ): LockPresenter = LockPresenterImpl(interactor, schedulers, state)

    @Provides
    @PerActivity
    internal fun provideInteractor(
            journal: Journal,
            schedulers: SchedulersFactory
    ): LockInteractor = LockInteractorImpl(journal, schedulers)

}