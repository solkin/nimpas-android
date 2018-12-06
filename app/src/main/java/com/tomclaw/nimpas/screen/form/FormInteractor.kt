package com.tomclaw.nimpas.screen.form

import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.screen.form.model.Widget
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Observable
import java.util.Random

interface FormInteractor {

    fun getWidgets(): Observable<List<Widget>>

}

class FormInteractorImpl(
        private val recordType: Int,
        private val groupId: Long,
        private val journal: Journal,
        private val schedulers: SchedulersFactory
) : FormInteractor {

    private val random = Random(System.currentTimeMillis())


    override fun getWidgets(): Observable<List<Widget>> {
        val widgets: List<Widget> = listOf(
                Widget.Label(1L, "Simple Label"),
                Widget.Input(2L, "First Input", ""),
                Widget.Input(4L, "Second Input", "Default Text")
        )
        return Observable.just(widgets)
                .subscribeOn(schedulers.io())
    }

}