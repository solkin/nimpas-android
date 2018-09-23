package com.tomclaw.nimpas.di

import android.app.Application
import android.content.Context
import com.tomclaw.nimpas.journal.Card
import com.tomclaw.nimpas.journal.GROUP_DEFAULT
import com.tomclaw.nimpas.journal.Group
import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.journal.JournalImpl
import com.tomclaw.nimpas.journal.Note
import com.tomclaw.nimpas.journal.Password
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
    internal fun provideJounal(): Journal {
        val file = File(app.filesDir, "journal.dat")
        return JournalImpl(file).apply {
            unlock("").subscribe()
//            val time = System.currentTimeMillis()
//            val records = listOf(
//                    Group(1, GROUP_DEFAULT, time, "Group title"),
//                    Password(2, GROUP_DEFAULT, time, "Pass Title", "Subtitle", "", "", ""),
//                    Card(3, GROUP_DEFAULT, time, "Card Title", "1234 5678 9012 3456", 0, "", 0),
//                    Note(4, GROUP_DEFAULT, time, "Note Title", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."),
//                    Note(5, GROUP_DEFAULT, time, "Note Title", "Lorem ipsum dolor sit amet."),
//                    Group(8, 1, time, "Other title"),
//                    Note(6, 1, time, "Some Note", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
//                    Note(7, 1, time, "Another Note", "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."),
//                    Password(9, 8, time, "Pass Title", "Subtitle", "", "", ""),
//                    Password(10, 8, time, "Pass Title", "Subtitle", "", "", "")
//            )
//            for (record in records) {
//                addRecord(record).subscribe()
//            }
        }
    }

}