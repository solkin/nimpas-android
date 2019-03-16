package com.tomclaw.nimpas.screen.user.switch.di

import android.content.Context
import android.os.Bundle
import com.tomclaw.nimpas.screen.user.switch.UserSwitchInteractor
import com.tomclaw.nimpas.screen.user.switch.UserSwitchInteractorImpl
import com.tomclaw.nimpas.screen.user.switch.UserSwitchPresenter
import com.tomclaw.nimpas.screen.user.switch.UserSwitchPresenterImpl
import com.tomclaw.nimpas.storage.Shelf
import com.tomclaw.nimpas.util.PerActivity
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Module
import dagger.Provides

@Module
class UserSwitchModule(
        private val context: Context,
        private val state: Bundle?
) {

    @Provides
    @PerActivity
    internal fun providePresenter(
            interactor: UserSwitchInteractor,
            schedulers: SchedulersFactory
    ): UserSwitchPresenter = UserSwitchPresenterImpl(interactor, schedulers, state)

    @Provides
    @PerActivity
    internal fun provideInteractor(
            shelf: Shelf,
            schedulers: SchedulersFactory
    ): UserSwitchInteractor = UserSwitchInteractorImpl(shelf, schedulers)

}