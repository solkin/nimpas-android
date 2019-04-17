package com.tomclaw.nimpas.screen.book.add.di

import com.tomclaw.nimpas.screen.book.add.BookAddActivity
import com.tomclaw.nimpas.util.PerActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [BookAddModule::class])
interface BookAddComponent {

    fun inject(activity: BookAddActivity)

}