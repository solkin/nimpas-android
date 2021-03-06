package com.tomclaw.nimpas.templates

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Observable
import io.reactivex.Single
import java.io.InputStream
import java.io.InputStreamReader

interface TemplateRepository {

    fun getTemplates(): Observable<List<Template>>

}

class TemplateRepositoryImpl(
        private val stream: InputStream,
        private val gson: Gson,
        private val schedulers: SchedulersFactory
) : TemplateRepository {

    private var templates: List<Template>? = null

    override fun getTemplates(): Observable<List<Template>> = Single
            .create<List<Template>> { emitter ->
                val result = templates ?: readTemplates().also { templates = it }
                emitter.onSuccess(result)
            }
            .toObservable()
            .subscribeOn(schedulers.io())

    private fun readTemplates(): List<Template> {
        InputStreamReader(stream).use { stream ->
            val listType = object : TypeToken<List<Template>>() {}.type
            return gson.fromJson(stream, listType)
        }
    }

}