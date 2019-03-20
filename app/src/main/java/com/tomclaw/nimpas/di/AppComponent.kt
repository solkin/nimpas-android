package com.tomclaw.nimpas.di

import com.tomclaw.nimpas.screen.form.di.FormComponent
import com.tomclaw.nimpas.screen.form.di.FormModule
import com.tomclaw.nimpas.screen.info.di.InfoComponent
import com.tomclaw.nimpas.screen.info.di.InfoModule
import com.tomclaw.nimpas.screen.lock.di.LockComponent
import com.tomclaw.nimpas.screen.lock.di.LockModule
import com.tomclaw.nimpas.screen.safe.di.SafeComponent
import com.tomclaw.nimpas.screen.safe.di.SafeModule
import com.tomclaw.nimpas.screen.start.di.StartComponent
import com.tomclaw.nimpas.screen.start.di.StartModule
import com.tomclaw.nimpas.screen.user.add.di.UserAddComponent
import com.tomclaw.nimpas.screen.user.add.di.UserAddModule
import com.tomclaw.nimpas.screen.user.list.di.UserListComponent
import com.tomclaw.nimpas.screen.user.list.di.UserListModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun safeComponent(module: SafeModule): SafeComponent

    fun formComponent(module: FormModule): FormComponent

    fun infoComponent(module: InfoModule): InfoComponent

    fun lockComponent(module: LockModule): LockComponent

    fun startComponent(module: StartModule): StartComponent

    fun userAddComponent(module: UserAddModule): UserAddComponent

    fun userListComponent(module: UserListModule): UserListComponent

}