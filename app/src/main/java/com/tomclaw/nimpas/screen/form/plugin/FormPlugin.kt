package com.tomclaw.nimpas.screen.form.plugin

import com.tomclaw.nimpas.screen.form.adapter.FormItem
import com.tomclaw.nimpas.templates.Template
import io.reactivex.Observable

interface FormPlugin {

    val action: String

    fun operation(template: Template, items: List<FormItem>): Observable<Unit>

}