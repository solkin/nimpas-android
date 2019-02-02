package com.tomclaw.nimpas.screen.form

import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.templates.Template
import com.tomclaw.nimpas.templates.TemplateRepository
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Completable
import io.reactivex.Observable


interface FormInteractor {

    fun getTemplate(id: Long = ID_ROOT): Observable<Template?>

    fun saveRecord(): Completable

}

class FormInteractorImpl(
        private val templateId: Long,
        private val groupId: Long,
        private val journal: Journal,
        private val templateRepository: TemplateRepository,
        private val schedulers: SchedulersFactory
) : FormInteractor {

    private var rootTemplate: Observable<Map<Long, Template>>? = null

    override fun getTemplate(id: Long): Observable<Template?> {
        return (rootTemplate ?: loadTemplate())
                .map { it[id] }
                .subscribeOn(schedulers.io())
    }

    override fun saveRecord(): Completable {
        return Completable.create { emitter ->
            emitter.onComplete()
        }
    }

    private fun loadTemplate() = templateRepository.getTemplates()
            .map {
                val root = Template(id = templateId, nested = it)
                flatten(root)
            }
            .doOnNext { rootTemplate = Observable.just(it) }

    private fun flatten(source: Template): Map<Long, Template> {
        val templates = mutableMapOf<Long, Template>().also { it[source.id] = source }
        source.nested?.forEach { templates.putAll(flatten(it)) }
        return templates
    }

}

const val ID_ROOT = 0L
