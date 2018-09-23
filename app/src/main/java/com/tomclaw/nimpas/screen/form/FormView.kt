package com.tomclaw.nimpas.screen.form

import android.view.View

interface FormView {

    fun showProgress()

    fun showContent()

}

class FormViewImpl(
        private val view: View
) : FormView {

    init {

    }

    override fun showProgress() {

    }

    override fun showContent() {

    }

}
