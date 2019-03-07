package com.tomclaw.nimpas.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.storage.Book
import com.tomclaw.nimpas.storage.BookImpl
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
    internal fun provideBook(): Book {
        val file = File(app.filesDir, "default.nmb")
        return BookImpl(file)
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