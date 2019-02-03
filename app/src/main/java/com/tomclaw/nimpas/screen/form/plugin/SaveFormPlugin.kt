package com.tomclaw.nimpas.screen.form.plugin

import com.tomclaw.nimpas.screen.form.adapter.FormItem
import com.tomclaw.nimpas.templates.Template
import io.reactivex.Completable
import io.reactivex.Observable

class SaveFormPlugin : FormPlugin {

    override val action: String = "save"

    override fun operation(
            template: Template,
            items: List<FormItem>
    ): Observable<Unit> = Completable.create { emitter ->
        emitter.onComplete()
    }.toObservable<Unit>()

}