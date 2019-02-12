package com.tomclaw.nimpas.screen.info.di

import com.tomclaw.nimpas.screen.info.InfoActivity
import com.tomclaw.nimpas.util.PerActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [InfoModule::class])
interface InfoComponent {

    fun inject(activity: InfoActivity)

}