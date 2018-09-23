package com.tomclaw.nimpas.screen.lock

import android.view.View

interface LockView {

    fun showProgress()

    fun showContent()

}

class LockViewImpl(
        private val view: View
) : LockView {

    init {

    }

    override fun showProgress() {

    }

    override fun showContent() {

    }

}
