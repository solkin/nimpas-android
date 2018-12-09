package com.tomclaw.nimpas.screen.form

import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.templates.Template
import com.tomclaw.nimpas.templates.TemplateRepository
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Observable

interface FormInteractor {

    fun getTemplates(): Observable<List<Template>>

}

class FormInteractorImpl(
        private val recordType: Int,
        private val groupId: Long,
        private val journal: Journal,
        private val templateRepository: TemplateRepository,
        private val schedulers: SchedulersFactory
) : FormInteractor {

    override fun getTemplates(): Observable<List<Template>> {
        return templateRepository.getTemplates()
                .subscribeOn(schedulers.io())
//        val widgets: List<Widget> = listOf(
//                Widget.Edit(id = 2L, hint = "Название", text = ""),
//                Widget.Header(id = 1L, text = "Данные для входа"),
//                Widget.Edit(id = 4L, hint = "Логин", text = ""),
//                Widget.Edit(id = 4L, hint = "Пароль", text = ""),
//                Widget.Header(id = 1L, text = "Дополнительно"),
//                Widget.Edit(id = 4L, hint = "URL", text = ""),
//                Widget.Edit(id = 4L, hint = "Описание", text = "")
//        )
    }

}