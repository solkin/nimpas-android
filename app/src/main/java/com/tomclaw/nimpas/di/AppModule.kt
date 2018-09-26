package com.tomclaw.nimpas.di

import android.app.Application
import android.content.Context
import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.journal.JournalImpl
import com.tomclaw.nimpas.util.SchedulersFactory
import com.tomclaw.nimpas.util.SchedulersFactoryImpl
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    internal fun provideContext(): Context = app

    @Provides
    @Singleton
    internal fun provideSchedulersFactory(): SchedulersFactory = SchedulersFactoryImpl()

    @Provides
    @Singleton
    internal fun provideJournal(): Journal {
        val file = File(app.filesDir, "journal.dat")
        return JournalImpl(file)
    }

}