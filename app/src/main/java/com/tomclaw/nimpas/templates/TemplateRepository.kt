package com.tomclaw.nimpas.templates

import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Observable
import java.io.File

interface TemplateRepository {

    fun getTemplates(): Observable<List<Template>>

}

class TemplateRepositoryImpl(
        private val file: File,
        private val schedulers: SchedulersFactory
) : TemplateRepository {

    override fun getTemplates(): Observable<List<Template>> {
        return Observable.just(readTemplates())
                .subscribeOn(schedulers.io())
    }

    private fun readTemplates(): List<Template> {
        return emptyList()
    }

}