package com.tomclaw.nimpas.screen.form.plugin

import io.reactivex.Completable
import io.reactivex.Observable

class SaveFormPlugin : FormPlugin {

    override val action: String = "save"

    override fun operation(): Observable<Unit> = Completable.create { emitter ->
        emitter.onComplete()
    }.toObservable<Unit>()

}