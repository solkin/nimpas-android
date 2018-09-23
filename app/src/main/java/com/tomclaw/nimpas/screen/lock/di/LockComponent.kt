package com.tomclaw.nimpas.screen.lock.di

import com.tomclaw.nimpas.screen.lock.LockActivity
import com.tomclaw.nimpas.util.PerActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [LockModule::class])
interface LockComponent {

    fun inject(activity: LockActivity)

}