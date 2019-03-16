package com.tomclaw.nimpas.screen.user.switch.di

import com.tomclaw.nimpas.screen.user.switch.UserSwitchActivity
import com.tomclaw.nimpas.util.PerActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [UserSwitchModule::class])
interface UserSwitchComponent {

    fun inject(activity: UserSwitchActivity)

}