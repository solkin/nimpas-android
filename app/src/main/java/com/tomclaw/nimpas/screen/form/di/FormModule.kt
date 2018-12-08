package com.tomclaw.nimpas.screen.form.di

import android.content.Context
import android.os.Bundle
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleAdapterPresenter
import com.avito.konveyor.blueprint.ItemBlueprint
import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.screen.form.FormInteractor
import com.tomclaw.nimpas.screen.form.FormInteractorImpl
import com.tomclaw.nimpas.screen.form.FormPresenter
import com.tomclaw.nimpas.screen.form.FormPresenterImpl
import com.tomclaw.nimpas.screen.form.WidgetConverter
import com.tomclaw.nimpas.screen.form.WidgetConverterImpl
import com.tomclaw.nimpas.screen.form.adapter.check.CheckItemBlueprint
import com.tomclaw.nimpas.screen.form.adapter.check.CheckItemPresenter
import com.tomclaw.nimpas.screen.form.adapter.edit.EditItemBlueprint
import com.tomclaw.nimpas.screen.form.adapter.edit.EditItemPresenter
import com.tomclaw.nimpas.screen.form.adapter.label.LabelItemBlueprint
import com.tomclaw.nimpas.screen.form.adapter.label.LabelItemPresenter
import com.tomclaw.nimpas.util.PerActivity
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
class FormModule(
        private val context: Context,
        private val recordType: Int,
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
            widgetConverter: WidgetConverter,
            schedulers: SchedulersFactory
    ): FormPresenter = FormPresenterImpl(
            interactor,
            adapterPresenter,
            widgetConverter,
            schedulers,
            state
    )

    @Provides
    @PerActivity
    internal fun provideInteractor(
            journal: Journal,
            schedulers: SchedulersFactory
    ): FormInteractor = FormInteractorImpl(recordType, groupId, journal, schedulers)

    @Provides
    @PerActivity
    internal fun provideRecordConverter(): WidgetConverter {
        return WidgetConverterImpl()
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
    internal fun provideInputItemBlueprint(
            presenter: EditItemPresenter
    ): ItemBlueprint<*, *> = EditItemBlueprint(presenter)

    @Provides
    @IntoSet
    @PerActivity
    internal fun provideLabelItemBlueprint(
            presenter: LabelItemPresenter
    ): ItemBlueprint<*, *> = LabelItemBlueprint(presenter)

    @Provides
    @IntoSet
    @PerActivity
    internal fun provideCheckItemBlueprint(
            presenter: CheckItemPresenter
    ): ItemBlueprint<*, *> = CheckItemBlueprint(presenter)

    @Provides
    @PerActivity
    internal fun provideInputItemPresenter(presenter: FormPresenter) =
            EditItemPresenter()

    @Provides
    @PerActivity
    internal fun provideLabelItemPresenter(presenter: FormPresenter) =
            LabelItemPresenter()

    @Provides
    @PerActivity
    internal fun provideCheckItemPresenter(presenter: FormPresenter) =
            CheckItemPresenter()

}