package com.tomclaw.nimpas.undo

import io.reactivex.Completable

interface UndoAction {

    val timeout: Long

    operator fun invoke(): Completable

}