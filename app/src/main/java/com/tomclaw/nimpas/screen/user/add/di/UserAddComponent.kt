package com.tomclaw.nimpas.screen.user.add.di

import com.tomclaw.nimpas.screen.user.add.UserAddActivity
import com.tomclaw.nimpas.util.PerActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [UserAddModule::class])
interface UserAddComponent {

    fun inject(activity: UserAddActivity)

}