package com.tomclaw.nimpas.screen.user.list.di

import android.content.Context
import android.os.Bundle
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleAdapterPresenter
import com.avito.konveyor.blueprint.ItemBlueprint
import com.tomclaw.nimpas.screen.user.list.BookConverter
import com.tomclaw.nimpas.screen.user.list.BookConverterImpl
import com.tomclaw.nimpas.screen.user.list.UserListInteractor
import com.tomclaw.nimpas.screen.user.list.UserListInteractorImpl
import com.tomclaw.nimpas.screen.user.list.UserListPresenter
import com.tomclaw.nimpas.screen.user.list.UserListPresenterImpl
import com.tomclaw.nimpas.screen.user.list.UserListResourceProvider
import com.tomclaw.nimpas.screen.user.list.UserListResourceProviderImpl
import com.tomclaw.nimpas.screen.user.list.adapter.user.UserItemBlueprint
import com.tomclaw.nimpas.screen.user.list.adapter.user.UserItemPresenter
import com.tomclaw.nimpas.storage.Shelf
import com.tomclaw.nimpas.util.PerActivity
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

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

    @Provides
    @PerActivity
    internal fun provideResourceProvider(): UserListResourceProvider {
        return UserListResourceProviderImpl(context.resources)
    }

    @Provides
    @PerActivity
    internal fun provideBookConverter(resourceProvider: UserListResourceProvider): BookConverter {
        return BookConverterImpl(resourceProvider)
    }

    @Provides
    @PerActivity
    internal fun provideAdapterPresenter(binder: ItemBinder): AdapterPresenter {
        return SimpleAdapterPresenter(binder, binder)
    }

    @Provides
    @PerActivity
    internal fun provideItemBinder(
            blueprintSet: Set<@JvmSuppressWildcards ItemBlueprint<*, *>>
    ): ItemBinder {
        return ItemBinder.Builder().apply {
            blueprintSet.forEach { registerItem(it) }
        }.build()
    }

    @Provides
    @IntoSet
    @PerActivity
    internal fun provideUserItemBlueprint(
            presenter: UserItemPresenter
    ): ItemBlueprint<*, *> = UserItemBlueprint(presenter)

    @Provides
    @PerActivity
    internal fun provideUserItemPresenter(presenter: UserListPresenter) =
            UserItemPresenter(presenter)

}