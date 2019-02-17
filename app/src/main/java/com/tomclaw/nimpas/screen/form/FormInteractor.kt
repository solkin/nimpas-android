package com.tomclaw.nimpas.screen.form

import com.tomclaw.nimpas.templates.Template
import com.tomclaw.nimpas.templates.TemplateRepository
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Observable


interface FormInteractor {

    fun getTemplate(id: Long = ID_ROOT): Observable<Template?>

}

class FormInteractorImpl(
        private val templateId: Long,
        private val override: Map<Long, Template>,
        private val templateRepository: TemplateRepository,
        private val schedulers: SchedulersFactory
) : FormInteractor {

    private var rootTemplate: Observable<Map<Long, Template>>? = null

    override fun getTemplate(id: Long): Observable<Template?> {
        return (rootTemplate ?: loadTemplate())
                .map { templates ->
                    override[id]?.let { template ->
                        templates[id]?.takeIf { it.version > template.version } ?: template
                    } ?: templates[id]
                }
                .subscribeOn(schedulers.io())
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
