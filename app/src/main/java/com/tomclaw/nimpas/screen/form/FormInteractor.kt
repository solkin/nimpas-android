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
                Widget.Label(id = 1L, text = "Simple Label"),
                Widget.Input(id = 2L, hint = "First Input", text = ""),
                Widget.Input(id = 4L, hint = "Second Input", text = "Default Text"),
                Widget.Check(id = 5L, text = "Second Input", checked = true)
        )
        return Observable.just(widgets)
                .subscribeOn(schedulers.io())
    }

}