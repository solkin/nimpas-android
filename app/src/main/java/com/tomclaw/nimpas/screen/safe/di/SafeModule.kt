package com.tomclaw.nimpas.screen.safe.di

import android.content.Context
import android.os.Bundle
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleAdapterPresenter
import com.avito.konveyor.blueprint.ItemBlueprint
import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.screen.safe.RecordConverter
import com.tomclaw.nimpas.screen.safe.RecordConverterImpl
import com.tomclaw.nimpas.screen.safe.SafeInteractor
import com.tomclaw.nimpas.screen.safe.SafeInteractorImpl
import com.tomclaw.nimpas.screen.safe.SafePresenter
import com.tomclaw.nimpas.screen.safe.SafePresenterImpl
import com.tomclaw.nimpas.screen.safe.adapter.card.CardItemBlueprint
import com.tomclaw.nimpas.screen.safe.adapter.card.CardItemPresenter
import com.tomclaw.nimpas.screen.safe.adapter.group.GroupItemBlueprint
import com.tomclaw.nimpas.screen.safe.adapter.group.GroupItemPresenter
import com.tomclaw.nimpas.screen.safe.adapter.note.NoteItemBlueprint
import com.tomclaw.nimpas.screen.safe.adapter.note.NoteItemPresenter
import com.tomclaw.nimpas.screen.safe.adapter.pass.PasswordItemBlueprint
import com.tomclaw.nimpas.screen.safe.adapter.pass.PasswordItemPresenter
import com.tomclaw.nimpas.templates.TemplateRepository
import com.tomclaw.nimpas.undo.Undoer
import com.tomclaw.nimpas.util.PerActivity
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Lazy
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
    internal fun provideAdapterPresenter(binder: ItemBinder): AdapterPresenter {
        return SimpleAdapterPresenter(binder, binder)
    }

    @Provides
    @PerActivity
    internal fun provideSafePresenter(
            interactor: SafeInteractor,
            adapterPresenter: Lazy<AdapterPresenter>,
            recordConverter: RecordConverter,
            templateRepository: TemplateRepository,
            undoer: Undoer,
            schedulers: SchedulersFactory
    ): SafePresenter = SafePresenterImpl(
            interactor,
            adapterPresenter,
            recordConverter,
            templateRepository,
            undoer,
            schedulers,
            state
    )

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
            presenter: PasswordItemPresenter
    ): ItemBlueprint<*, *> = PasswordItemBlueprint(presenter)

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
    internal fun provideGroupItemPresenter(presenter: SafePresenter) =
            GroupItemPresenter(presenter)

    @Provides
    @PerActivity
    internal fun provideWebItemPresenter(presenter: SafePresenter) =
            PasswordItemPresenter(presenter)

    @Provides
    @PerActivity
    internal fun provideCardItemPresenter(presenter: SafePresenter) =
            CardItemPresenter(presenter)

    @Provides
    @PerActivity
    internal fun provideNoteItemPresenter(presenter: SafePresenter) =
            NoteItemPresenter(presenter)

}