package com.tomclaw.nimpas.screen.info

import android.os.Bundle

interface InfoPresenter {

    fun attachView(view: InfoView)

    fun detachView()

    fun attachRouter(router: InfoRouter)

    fun detachRouter()

    fun saveState(): Bundle

    fun onBackPressed()

    interface InfoRouter {

        fun leaveScreen()

    }

}
