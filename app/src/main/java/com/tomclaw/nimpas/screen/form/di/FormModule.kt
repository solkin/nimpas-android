package com.tomclaw.nimpas.screen.form.di

import android.os.Bundle
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleAdapterPresenter
import com.avito.konveyor.blueprint.ItemBlueprint
import com.jakewharton.rxrelay2.PublishRelay
import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.screen.form.FormInteractor
import com.tomclaw.nimpas.screen.form.FormInteractorImpl
import com.tomclaw.nimpas.screen.form.FormPresenter
import com.tomclaw.nimpas.screen.form.FormPresenterImpl
import com.tomclaw.nimpas.screen.form.adapter.FormEvent
import com.tomclaw.nimpas.screen.form.adapter.action.ActionItemBlueprint
import com.tomclaw.nimpas.screen.form.adapter.action.ActionItemPresenter
import com.tomclaw.nimpas.screen.form.adapter.button.ButtonItemBlueprint
import com.tomclaw.nimpas.screen.form.adapter.button.ButtonItemPresenter
import com.tomclaw.nimpas.screen.form.adapter.check.CheckItemBlueprint
import com.tomclaw.nimpas.screen.form.adapter.check.CheckItemPresenter
import com.tomclaw.nimpas.screen.form.adapter.edit.EditItemBlueprint
import com.tomclaw.nimpas.screen.form.adapter.edit.EditItemPresenter
import com.tomclaw.nimpas.screen.form.adapter.header.HeaderItemBlueprint
import com.tomclaw.nimpas.screen.form.adapter.header.HeaderItemPresenter
import com.tomclaw.nimpas.screen.form.converter.FieldConverter
import com.tomclaw.nimpas.screen.form.converter.FieldConverterImpl
import com.tomclaw.nimpas.screen.form.converter.TemplateConverter
import com.tomclaw.nimpas.screen.form.converter.TemplateConverterImpl
import com.tomclaw.nimpas.screen.form.plugin.FormPlugin
import com.tomclaw.nimpas.screen.form.plugin.SaveFormPlugin
import com.tomclaw.nimpas.templates.TemplateRepository
import com.tomclaw.nimpas.util.PerActivity
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
class FormModule(
        private val templateId: Long,
        private val groupId: Long,
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
            interactor: FormInteractor,
            adapterPresenter: Lazy<AdapterPresenter>,
            templateConverter: TemplateConverter,
            fieldConverter: FieldConverter,
            events: PublishRelay<FormEvent>,
            plugins: Set<@JvmSuppressWildcards FormPlugin>,
            schedulers: SchedulersFactory
    ): FormPresenter = FormPresenterImpl(
            templateId,
            interactor,
            adapterPresenter,
            templateConverter,
            fieldConverter,
            events,
            plugins,
            schedulers,
            state
    )

    @Provides
    @PerActivity
    internal fun provideInteractor(
            journal: Journal,
            templateRepository: TemplateRepository,
            schedulers: SchedulersFactory
    ): FormInteractor = FormInteractorImpl(
            templateId,
            groupId,
            journal,
            templateRepository,
            schedulers
    )

    @Provides
    @PerActivity
    internal fun provideTemplateConverter(): TemplateConverter {
        return TemplateConverterImpl()
    }

    @Provides
    @PerActivity
    internal fun provideFieldConverter(): FieldConverter {
        return FieldConverterImpl()
    }

    @Provides
    @PerActivity
    internal fun provideEventsRelay(): PublishRelay<FormEvent> = PublishRelay.create()

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
    internal fun provideActionItemBlueprint(
            presenter: ActionItemPresenter
    ): ItemBlueprint<*, *> = ActionItemBlueprint(presenter)

    @Provides
    @IntoSet
    @PerActivity
    internal fun provideEditItemBlueprint(
            presenter: EditItemPresenter
    ): ItemBlueprint<*, *> = EditItemBlueprint(presenter)

    @Provides
    @IntoSet
    @PerActivity
    internal fun provideHeaderItemBlueprint(
            presenter: HeaderItemPresenter
    ): ItemBlueprint<*, *> = HeaderItemBlueprint(presenter)

    @Provides
    @IntoSet
    @PerActivity
    internal fun provideCheckItemBlueprint(
            presenter: CheckItemPresenter
    ): ItemBlueprint<*, *> = CheckItemBlueprint(presenter)

    @Provides
    @IntoSet
    @PerActivity
    internal fun provideButtonItemBlueprint(
            presenter: ButtonItemPresenter
    ): ItemBlueprint<*, *> = ButtonItemBlueprint(presenter)

    @Provides
    @PerActivity
    internal fun provideActionItemPresenter(events: PublishRelay<FormEvent>) =
            ActionItemPresenter(events)

    @Provides
    @PerActivity
    internal fun provideEditItemPresenter(presenter: FormPresenter) =
            EditItemPresenter()

    @Provides
    @PerActivity
    internal fun provideHeaderItemPresenter(presenter: FormPresenter) =
            HeaderItemPresenter()

    @Provides
    @PerActivity
    internal fun provideCheckItemPresenter(presenter: FormPresenter) =
            CheckItemPresenter()

    @Provides
    @PerActivity
    internal fun provideButtonItemPresenter(events: PublishRelay<FormEvent>) =
            ButtonItemPresenter(events)

    @Provides
    @IntoSet
    @PerActivity
    internal fun provideSaveFormPlugin(journal: Journal): FormPlugin {
        return SaveFormPlugin(groupId, journal)
    }

}