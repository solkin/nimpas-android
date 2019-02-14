package com.tomclaw.nimpas.screen.info.di

import android.os.Bundle
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleAdapterPresenter
import com.avito.konveyor.blueprint.ItemBlueprint
import com.tomclaw.nimpas.journal.Record
import com.tomclaw.nimpas.screen.form.converter.FieldConverter
import com.tomclaw.nimpas.screen.info.InfoInteractor
import com.tomclaw.nimpas.screen.info.InfoInteractorImpl
import com.tomclaw.nimpas.screen.info.InfoPresenter
import com.tomclaw.nimpas.screen.info.InfoPresenterImpl
import com.tomclaw.nimpas.screen.info.adapter.header.HeaderItemBlueprint
import com.tomclaw.nimpas.screen.info.adapter.header.HeaderItemPresenter
import com.tomclaw.nimpas.util.PerActivity
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
class InfoModule(
        private val record: Record,
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
            schedulers: SchedulersFactory
    ): InfoPresenter = InfoPresenterImpl(
            interactor,
            adapterPresenter,
            schedulers,
            state
    )

    @Provides
    @PerActivity
    internal fun provideInteractor(
            schedulers: SchedulersFactory
    ): InfoInteractor = InfoInteractorImpl(
            schedulers
    )

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

}