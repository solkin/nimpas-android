package com.tomclaw.nimpas.screen.book.list.di

import android.content.Context
import android.os.Bundle
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleAdapterPresenter
import com.avito.konveyor.blueprint.ItemBlueprint
import com.tomclaw.nimpas.screen.book.list.BookConverter
import com.tomclaw.nimpas.screen.book.list.BookConverterImpl
import com.tomclaw.nimpas.screen.book.list.BookListInteractor
import com.tomclaw.nimpas.screen.book.list.BookListInteractorImpl
import com.tomclaw.nimpas.screen.book.list.BookListPresenter
import com.tomclaw.nimpas.screen.book.list.BookListPresenterImpl
import com.tomclaw.nimpas.screen.book.list.BookListResourceProvider
import com.tomclaw.nimpas.screen.book.list.BookListResourceProviderImpl
import com.tomclaw.nimpas.screen.book.list.adapter.book.BookItemBlueprint
import com.tomclaw.nimpas.screen.book.list.adapter.book.BookItemPresenter
import com.tomclaw.nimpas.storage.Shelf
import com.tomclaw.nimpas.util.PerActivity
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
class BookListModule(
        private val context: Context,
        private val state: Bundle?
) {

    @Provides
    @PerActivity
    internal fun providePresenter(
            interactor: BookListInteractor,
            adapterPresenter: Lazy<AdapterPresenter>,
            bookConverter: BookConverter,
            schedulers: SchedulersFactory
    ): BookListPresenter = BookListPresenterImpl(
            interactor,
            adapterPresenter,
            bookConverter,
            schedulers,
            state
    )

    @Provides
    @PerActivity
    internal fun provideInteractor(
            shelf: Shelf,
            schedulers: SchedulersFactory
    ): BookListInteractor = BookListInteractorImpl(shelf, schedulers)

    @Provides
    @PerActivity
    internal fun provideResourceProvider(): BookListResourceProvider {
        return BookListResourceProviderImpl(context.resources)
    }

    @Provides
    @PerActivity
    internal fun provideBookConverter(resourceProvider: BookListResourceProvider): BookConverter {
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
    internal fun provideBookItemBlueprint(
            presenter: BookItemPresenter
    ): ItemBlueprint<*, *> = BookItemBlueprint(presenter)

    @Provides
    @PerActivity
    internal fun provideBookItemPresenter(presenter: BookListPresenter) =
            BookItemPresenter(presenter)

}