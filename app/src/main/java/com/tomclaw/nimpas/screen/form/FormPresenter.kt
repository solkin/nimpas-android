package com.tomclaw.nimpas.screen.form

import android.os.Bundle
import com.avito.konveyor.adapter.AdapterPresenter
import com.tomclaw.nimpas.templates.Field
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
        private val interactor: FormInteractor,
        private val adapterPresenter: Lazy<AdapterPresenter>,
        private val widgetConverter: WidgetConverter,
        private val schedulers: SchedulersFactory,
        state: Bundle?
) : FormPresenter {

    private var view: FormView? = null
    private var router: FormPresenter.FormRouter? = null

    private val subscriptions = CompositeDisposable()

    private var navigation: Set<String> = state?.getStringArray(KEY_NAVIGATION)?.toMutableSet()
            ?: mutableSetOf(ID_ROOT)

    override fun attachView(view: FormView) {
        this.view = view
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
        putStringArray(KEY_NAVIGATION, navigation.toTypedArray())
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
        when {
            template == null -> throw IllegalStateException("Template not found")
            template.nested != null -> showNestedTemplates(template.nested)
            template.fields != null -> showFields(template.fields)
            else -> throw IllegalStateException("Template has no neither nested items nor fields")
        }
    }

    private fun showNestedTemplates(templates: List<Template>) {
//        val items = templates.asSequence()
//                .map { widgetConverter.convert(it) }
//                .toList()
//        val dataSource = ListDataSource(items)
//        adapterPresenter.get().onDataSourceChanged(dataSource)
//        view?.contentUpdated()
    }

    private fun showFields(fields: List<Field>) {

    }

    private fun onError(it: Throwable) {}

    override fun onBackPressed() {
        router?.leaveScreen()
    }

}

private const val KEY_NAVIGATION = "navigation"
