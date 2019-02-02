package com.tomclaw.nimpas.screen.form.plugin

import io.reactivex.Observable

interface FormPlugin {

    val action: String

    fun operation(): Observable<Unit>

}