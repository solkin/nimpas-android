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
                Widget.Edit(id = 2L, hint = "Название", text = ""),
                Widget.Header(id = 1L, text = "Данные для входа"),
                Widget.Edit(id = 4L, hint = "Логин", text = ""),
                Widget.Edit(id = 4L, hint = "Пароль", text = ""),
                Widget.Header(id = 1L, text = "Дополнительно"),
                Widget.Edit(id = 4L, hint = "URL", text = ""),
                Widget.Edit(id = 4L, hint = "Описание", text = "")
        )
        return Observable.just(widgets)
                .subscribeOn(schedulers.io())
    }

}