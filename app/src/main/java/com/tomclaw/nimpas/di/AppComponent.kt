package com.tomclaw.nimpas.di

import com.tomclaw.nimpas.screen.form.di.FormComponent
import com.tomclaw.nimpas.screen.form.di.FormModule
import com.tomclaw.nimpas.screen.safe.di.SafeComponent
import com.tomclaw.nimpas.screen.safe.di.SafeModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun safeComponent(module: SafeModule): SafeComponent

    fun formComponent(module: FormModule): FormComponent

}