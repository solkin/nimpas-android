package com.tomclaw.nimpas.screen.user.list.di

import com.tomclaw.nimpas.screen.user.list.UserListActivity
import com.tomclaw.nimpas.util.PerActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [UserListModule::class])
interface UserListComponent {

    fun inject(activity: UserListActivity)

}