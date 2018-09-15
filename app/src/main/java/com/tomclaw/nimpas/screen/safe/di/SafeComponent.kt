package com.tomclaw.nimpas.screen.safe.di

import com.tomclaw.nimpas.screen.safe.SafeActivity
import com.tomclaw.nimpas.util.PerActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [SafeModule::class])
interface SafeComponent {

    fun inject(activity: SafeActivity)

}