package com.tomclaw.nimpas.screen.safe.di

import android.content.Context
import android.os.Bundle
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.screen.safe.RecordConverter
import com.tomclaw.nimpas.screen.safe.RecordConverterImpl
import com.tomclaw.nimpas.screen.safe.SafeInteractor
import com.tomclaw.nimpas.screen.safe.SafeInteractorImpl
import com.tomclaw.nimpas.screen.safe.SafePresenter
import com.tomclaw.nimpas.screen.safe.SafePresenterImpl
import com.tomclaw.nimpas.screen.safe.adapter.blueprint.CardItemBlueprint
import com.tomclaw.nimpas.screen.safe.adapter.blueprint.GroupItemBlueprint
import com.tomclaw.nimpas.screen.safe.adapter.blueprint.NoteItemBlueprint
import com.tomclaw.nimpas.screen.safe.adapter.blueprint.WebItemBlueprint
import com.tomclaw.nimpas.screen.safe.adapter.presenter.CardItemPresenter
import com.tomclaw.nimpas.screen.safe.adapter.presenter.GroupItemPresenter
import com.tomclaw.nimpas.screen.safe.adapter.presenter.NoteItemPresenter
import com.tomclaw.nimpas.screen.safe.adapter.presenter.WebItemPresenter
import com.tomclaw.nimpas.util.DataProvider
import com.tomclaw.nimpas.util.PerActivity
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
class SafeModule(
        private val context: Context,
        private val state: Bundle?
) {

    @Provides
    @PerActivity
    internal fun provideSafePresenter(
            interactor: SafeInteractor,
            dataProvider: DataProvider<Item>,
            recordConverter: RecordConverter,
            schedulers: SchedulersFactory
    ): SafePresenter {
        return SafePresenterImpl(interactor, dataProvider, recordConverter, schedulers, state)
    }

    @Provides
    @PerActivity
    internal fun provideSafeInteractor(
            journal: Journal,
            schedulers: SchedulersFactory
    ): SafeInteractor {
        return SafeInteractorImpl(journal, schedulers)
    }

    @Provides
    @PerActivity
    internal fun provideDataProvider(): DataProvider<Item> {
        return DataProvider()
    }

    @Provides
    @PerActivity
    internal fun provideRecordConverter(): RecordConverter {
        return RecordConverterImpl()
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
    internal fun provideGroupItemBlueprint(
            presenter: GroupItemPresenter
    ): ItemBlueprint<*, *> = GroupItemBlueprint(presenter)

    @Provides
    @IntoSet
    @PerActivity
    internal fun provideWebItemBlueprint(
            presenter: WebItemPresenter
    ): ItemBlueprint<*, *> = WebItemBlueprint(presenter)

    @Provides
    @IntoSet
    @PerActivity
    internal fun provideCardItemBlueprint(
            presenter: CardItemPresenter
    ): ItemBlueprint<*, *> = CardItemBlueprint(presenter)

    @Provides
    @IntoSet
    @PerActivity
    internal fun provideNoteItemBlueprint(
            presenter: NoteItemPresenter
    ): ItemBlueprint<*, *> = NoteItemBlueprint(presenter)

    @Provides
    @PerActivity
    internal fun provideGroupItemPresenter() = GroupItemPresenter()

    @Provides
    @PerActivity
    internal fun provideWebItemPresenter() = WebItemPresenter()

    @Provides
    @PerActivity
    internal fun provideCardItemPresenter() = CardItemPresenter()

    @Provides
    @PerActivity
    internal fun provideNoteItemPresenter() = NoteItemPresenter()

}