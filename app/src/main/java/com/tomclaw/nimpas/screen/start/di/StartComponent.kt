package com.tomclaw.nimpas.screen.start.di

import com.tomclaw.nimpas.screen.start.StartActivity
import com.tomclaw.nimpas.util.PerActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [StartModule::class])
interface StartComponent {

    fun inject(activity: StartActivity)

}