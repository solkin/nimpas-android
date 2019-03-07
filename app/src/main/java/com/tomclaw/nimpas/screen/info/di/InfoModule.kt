package com.tomclaw.nimpas.screen.info.di

import android.content.res.Resources
import android.os.Bundle
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleAdapterPresenter
import com.avito.konveyor.blueprint.ItemBlueprint
import com.tomclaw.nimpas.screen.info.InfoResourceProvider
import com.tomclaw.nimpas.screen.info.InfoResourceProviderImpl
import com.tomclaw.nimpas.storage.Book
import com.tomclaw.nimpas.screen.info.InfoInteractor
import com.tomclaw.nimpas.screen.info.InfoInteractorImpl
import com.tomclaw.nimpas.screen.info.InfoPresenter
import com.tomclaw.nimpas.screen.info.InfoPresenterImpl
import com.tomclaw.nimpas.screen.info.adapter.check.CheckItemBlueprint
import com.tomclaw.nimpas.screen.info.adapter.check.CheckItemPresenter
import com.tomclaw.nimpas.screen.info.adapter.header.HeaderItemBlueprint
import com.tomclaw.nimpas.screen.info.adapter.header.HeaderItemPresenter
import com.tomclaw.nimpas.screen.info.adapter.text.TextItemBlueprint
import com.tomclaw.nimpas.screen.info.adapter.text.TextItemPresenter
import com.tomclaw.nimpas.screen.info.converter.FieldConverter
import com.tomclaw.nimpas.screen.info.converter.FieldConverterImpl
import com.tomclaw.nimpas.undo.Undoer
import com.tomclaw.nimpas.util.PerActivity
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
class InfoModule(
        private val recordId: Long,
        private val resources: Resources,
        private val state: Bundle?
) {

    @Provides
    @PerActivity
    internal fun provideAdapterPresenter(binder: ItemBinder): AdapterPresenter {
        return SimpleAdapterPresenter(binder, binder)
    }

    @Provides
    @PerActivity
    internal fun providePresenter(
            interactor: InfoInteractor,
            adapterPresenter: Lazy<AdapterPresenter>,
            fieldConverter: FieldConverter,
            resourceProvider: InfoResourceProvider,
            undoer: Undoer,
            schedulers: SchedulersFactory
    ): InfoPresenter = InfoPresenterImpl(
            recordId,
            interactor,
            adapterPresenter,
            fieldConverter,
            resourceProvider,
            undoer,
            schedulers,
            state
    )

    @Provides
    @PerActivity
    internal fun provideInteractor(
            book: Book,
            schedulers: SchedulersFactory
    ): InfoInteractor = InfoInteractorImpl(
            book,
            schedulers
    )

    @Provides
    @PerActivity
    internal fun provideFieldConverter(): FieldConverter {
        return FieldConverterImpl()
    }

    @Provides
    @PerActivity
    internal fun provideResourceProvider(): InfoResourceProvider {
        return InfoResourceProviderImpl(resources)
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
    internal fun provideHeaderItemBlueprint(
            presenter: HeaderItemPresenter
    ): ItemBlueprint<*, *> = HeaderItemBlueprint(presenter)

    @Provides
    @PerActivity
    internal fun provideHeaderItemPresenter() =
            HeaderItemPresenter()

    @Provides
    @IntoSet
    @PerActivity
    internal fun provideTextItemBlueprint(
            presenter: TextItemPresenter
    ): ItemBlueprint<*, *> = TextItemBlueprint(presenter)

    @Provides
    @PerActivity
    internal fun provideTextItemPresenter() =
            TextItemPresenter()

    @Provides
    @IntoSet
    @PerActivity
    internal fun provideCheckItemBlueprint(
            presenter: CheckItemPresenter
    ): ItemBlueprint<*, *> = CheckItemBlueprint(presenter)

    @Provides
    @PerActivity
    internal fun provideCheckItemPresenter() =
            CheckItemPresenter()

}