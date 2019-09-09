package com.tomclaw.nimpas.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.storage.Shelf
import com.tomclaw.nimpas.storage.ShelfImpl
import com.tomclaw.nimpas.templates.TemplateRepository
import com.tomclaw.nimpas.templates.TemplateRepositoryImpl
import com.tomclaw.nimpas.undo.Undoer
import com.tomclaw.nimpas.undo.UndoerImpl
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
    internal fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    internal fun provideSchedulersFactory(): SchedulersFactory = SchedulersFactoryImpl()

    @Provides
    @Singleton
    internal fun provideShelf(schedulers: SchedulersFactory): Shelf {
        return ShelfImpl(File(app.filesDir, SAFE_DIR), schedulers)
    }

    @Provides
    @Singleton
    internal fun provideTemplateRepository(
            gson: Gson,
            schedulersFactory: SchedulersFactory
    ): TemplateRepository {
        val stream = app.resources.openRawResource(R.raw.templates)
        return TemplateRepositoryImpl(stream, gson, schedulersFactory)
    }

    @Provides
    @Singleton
    internal fun provideUndoer(): Undoer = UndoerImpl()

}

private const val SAFE_DIR = "safe"