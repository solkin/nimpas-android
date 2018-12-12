package com.tomclaw.nimpas.screen.form

import android.os.Bundle
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.data_source.ListDataSource
import com.tomclaw.nimpas.templates.Template
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Lazy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign


interface FormPresenter {

    fun attachView(view: FormView)

    fun detachView()

    fun attachRouter(router: FormRouter)

    fun detachRouter()

    fun saveState(): Bundle

    fun onBackPressed()

    interface FormRouter {

        fun leaveScreen()

    }

}

class FormPresenterImpl(
        private val templateId: Long,
        private val interactor: FormInteractor,
        private val adapterPresenter: Lazy<AdapterPresenter>,
        private val templateConverter: TemplateConverter,
        private val fieldConverter: FieldConverter,
        private val schedulers: SchedulersFactory,
        state: Bundle?
) : FormPresenter {

    private var view: FormView? = null
    private var router: FormPresenter.FormRouter? = null

    private val subscriptions = CompositeDisposable()

    private var navigation: Set<Long> = state?.getLongArray(KEY_NAVIGATION)?.toMutableSet()
            ?: mutableSetOf(templateId)

    override fun attachView(view: FormView) {
        this.view = view

        subscriptions += view.navigationClicks().subscribe {
            router?.leaveScreen()
        }

        loadTemplates()
    }

    override fun detachView() {
        subscriptions.clear()
        this.view = null
    }

    override fun attachRouter(router: FormPresenter.FormRouter) {
        this.router = router
    }

    override fun detachRouter() {
        this.router = null
    }

    override fun saveState() = Bundle().apply {
        putLongArray(KEY_NAVIGATION, navigation.toLongArray())
    }

    private fun loadTemplates() {
        subscriptions += interactor.getTemplate(navigation.last())
                .observeOn(schedulers.mainThread())
                .doOnSubscribe { view?.showProgress() }
                .doAfterTerminate { view?.showContent() }
                .subscribe(
                        { onLoaded(it) },
                        { onError(it) }
                )
    }

    private fun onLoaded(template: Template?) {
        val items = when {
            template == null -> throw IllegalStateException("Template not found")
            template.nested != null -> template.nested.asSequence()
                    .map { templateConverter.convert(it) }
                    .toList()
            template.fields != null -> template.fields.asSequence()
                    .map { fieldConverter.convert(it) }
                    .toList()
            else -> throw IllegalStateException("Template has no neither nested items nor fields")
        }
        val dataSource = ListDataSource(items)
        adapterPresenter.get().onDataSourceChanged(dataSource)
        view?.contentUpdated()
    }

    private fun onError(it: Throwable) {}

    override fun onBackPressed() {
        router?.leaveScreen()
    }

}

private const val KEY_NAVIGATION = "navigation"
