package com.tomclaw.nimpas.screen.form

import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.templates.Template
import com.tomclaw.nimpas.templates.TemplateRepository
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Observable


interface FormInteractor {

    fun getTemplate(id: String = ID_ROOT): Observable<Template?>

}

class FormInteractorImpl(
        private val recordType: Int,
        private val groupId: Long,
        private val journal: Journal,
        private val templateRepository: TemplateRepository,
        private val schedulers: SchedulersFactory
) : FormInteractor {

    private var rootTemplate: Observable<Map<String, Template>>? = null

    override fun getTemplate(id: String): Observable<Template?> {
        return (rootTemplate ?: loadRootTemplate())
                .map { it[id] }
                .subscribeOn(schedulers.io())
    }

    private fun loadRootTemplate() = templateRepository.getTemplates()
            .map {
                val root = Template(id = ID_ROOT, nested = it)
                flatten(root)
            }
            .doOnNext { rootTemplate = Observable.just(it) }

    private fun flatten(source: Template): Map<String, Template> {
        val templates = mutableMapOf<String, Template>().also { it[source.id] = source }
        source.nested?.forEach { templates.putAll(flatten(it)) }
        return templates
    }

}

const val ID_ROOT = "root"
