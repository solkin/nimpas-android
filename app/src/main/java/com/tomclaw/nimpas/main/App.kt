package com.tomclaw.nimpas.main

import android.app.Application
import com.tomclaw.nimpas.di.AppComponent
import com.tomclaw.nimpas.di.AppModule
import com.tomclaw.nimpas.di.DaggerAppComponent

class App : Application() {

    lateinit var component: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        component = buildComponent()
    }

    private fun buildComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

}

fun Application.getComponent(): AppComponent {
    return (this as App).component
}