package com.tomclaw.nimpas.screen.book.importing.di

import com.tomclaw.nimpas.screen.book.importing.BookImportActivity
import com.tomclaw.nimpas.util.PerActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [BookImportModule::class])
interface BookImportComponent {

    fun inject(activity: BookImportActivity)

}