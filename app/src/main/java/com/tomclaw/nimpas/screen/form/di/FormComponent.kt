package com.tomclaw.nimpas.screen.form.di

import com.tomclaw.nimpas.screen.form.FormActivity
import com.tomclaw.nimpas.util.PerActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [FormModule::class])
interface FormComponent {

    fun inject(activity: FormActivity)

}