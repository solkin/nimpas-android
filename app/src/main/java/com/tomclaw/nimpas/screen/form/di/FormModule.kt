package com.tomclaw.nimpas.screen.form.di

import android.content.Context
import android.os.Bundle
import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.screen.form.FormInteractor
import com.tomclaw.nimpas.screen.form.FormInteractorImpl
import com.tomclaw.nimpas.screen.form.FormPresenter
import com.tomclaw.nimpas.screen.form.FormPresenterImpl
import com.tomclaw.nimpas.util.PerActivity
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Module
import dagger.Provides

@Module
class FormModule(
        private val context: Context,
        private val recordType: Int,
        private val groupId: Long,
        private val state: Bundle?
) {

    @Provides
    @PerActivity
    internal fun providePresenter(
            interactor: FormInteractor,
            schedulers: SchedulersFactory
    ): FormPresenter = FormPresenterImpl(interactor, schedulers, state)

    @Provides
    @PerActivity
    internal fun provideInteractor(
            journal: Journal,
            schedulers: SchedulersFactory
    ): FormInteractor = FormInteractorImpl(recordType, groupId, journal, schedulers)

}