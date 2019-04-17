package com.tomclaw.nimpas.screen.book.list.di

import com.tomclaw.nimpas.screen.book.list.BookListActivity
import com.tomclaw.nimpas.util.PerActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [BookListModule::class])
interface BookListComponent {

    fun inject(activity: BookListActivity)

}