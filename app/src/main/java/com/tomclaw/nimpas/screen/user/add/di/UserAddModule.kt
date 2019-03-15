package com.tomclaw.nimpas.screen.user.add.di

import android.content.Context
import android.os.Bundle
import com.tomclaw.nimpas.screen.user.add.UserAddInteractor
import com.tomclaw.nimpas.screen.user.add.UserAddInteractorImpl
import com.tomclaw.nimpas.screen.user.add.UserAddPresenter
import com.tomclaw.nimpas.screen.user.add.UserAddPresenterImpl
import com.tomclaw.nimpas.storage.Shelf
import com.tomclaw.nimpas.util.PerActivity
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Module
import dagger.Provides

@Module
class UserAddModule(
        private val context: Context,
        private val state: Bundle?
) {

    @Provides
    @PerActivity
    internal fun providePresenter(
            interactor: UserAddInteractor,
            schedulers: SchedulersFactory
    ): UserAddPresenter = UserAddPresenterImpl(interactor, schedulers, state)

    @Provides
    @PerActivity
    internal fun provideInteractor(
            shelf: Shelf,
            schedulers: SchedulersFactory
    ): UserAddInteractor = UserAddInteractorImpl(shelf, schedulers)

}