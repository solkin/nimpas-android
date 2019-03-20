package com.tomclaw.nimpas.screen.user.list.di

import android.content.Context
import android.os.Bundle
import com.tomclaw.nimpas.screen.user.list.UserListInteractor
import com.tomclaw.nimpas.screen.user.list.UserListInteractorImpl
import com.tomclaw.nimpas.screen.user.list.UserListPresenter
import com.tomclaw.nimpas.screen.user.list.UserListPresenterImpl
import com.tomclaw.nimpas.storage.Shelf
import com.tomclaw.nimpas.util.PerActivity
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Module
import dagger.Provides

@Module
class UserListModule(
        private val context: Context,
        private val state: Bundle?
) {

    @Provides
    @PerActivity
    internal fun providePresenter(
            interactor: UserListInteractor,
            schedulers: SchedulersFactory
    ): UserListPresenter = UserListPresenterImpl(interactor, schedulers, state)

    @Provides
    @PerActivity
    internal fun provideInteractor(
            shelf: Shelf,
            schedulers: SchedulersFactory
    ): UserListInteractor = UserListInteractorImpl(shelf, schedulers)

}