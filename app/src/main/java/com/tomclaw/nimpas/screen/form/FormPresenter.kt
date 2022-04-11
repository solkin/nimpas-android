package com.tomclaw.nimpas.screen.form

import android.os.Bundle
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.data_source.ListDataSource
import com.tomclaw.nimpas.screen.form.adapter.FormEvent
import com.tomclaw.nimpas.screen.form.adapter.FormItem
import com.tomclaw.nimpas.screen.form.converter.FieldConverter
import com.tomclaw.nimpas.screen.form.converter.TemplateConverter
import com.tomclaw.nimpas.screen.form.plugin.FormPlugin
import com.tomclaw.nimpas.templates.Template
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Lazy
import io.reactivex.Observable
import io.reactivex.Single
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

        fun leaveScreen(changed: Boolean)

    }

}

@Suppress("SuspiciousCollectionReassignment")
class FormPresenterImpl(
    private val templateId: Long,
    private val fields: Map<String, String>,
    private val interactor: FormInteractor,
    private val adapterPresenter: Lazy<AdapterPresenter>,
    private val templateConverter: TemplateConverter,
    private val fieldConverter: FieldConverter,
    private val events: Observable<FormEvent>,
    private val plugins: Set<FormPlugin>,
    private val schedulers: SchedulersFactory,
    state: Bundle?
) : FormPresenter {

    private var view: FormView? = null
    private var router: FormPresenter.FormRouter? = null

    private val subscriptions = CompositeDisposable()

    private var navigation: Set<Long> = state?.getLongArray(KEY_NAVIGATION)?.toMutableSet()
        ?: mutableSetOf(templateId)
    private var template: Template? = state?.getParcelable(KEY_TEMPLATE)
    private var items: List<FormItem>? = state?.getParcelableArrayList(KEY_ITEMS)

    override fun attachView(view: FormView) {
        this.view = view

        subscriptions += view.navigationClicks().subscribe {
            onBackPressed()
        }

        subscriptions += events.subscribe { event ->
            when (event) {
                is FormEvent.ActionClicked -> navigate(event.item.id)
                is FormEvent.ButtonClicked -> execute(event.item.action)
                else -> {}
            }
        }

        items?.let { onReady(it) } ?: loadTemplate()
    }

    private fun navigate(id: Long) {
        navigation += id
        loadTemplate()
    }

    private fun execute(action: String) {
        plugins.filter { it.action == action }.forEach { runPluginOperation(it) }
    }

    private fun runPluginOperation(plugin: FormPlugin) {
        val template = template ?: return
        val items = items ?: return
        subscriptions += plugin.operation(template, items)
            .observeOn(schedulers.mainThread())
            .doOnSubscribe { view?.showProgress() }
            .doAfterTerminate { view?.showContent() }
            .subscribe(
                { onCompleted() },
                { onError(it) }
            )
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
        putParcelable(KEY_TEMPLATE, template)
        putParcelableArrayList(KEY_ITEMS, items?.let { ArrayList(items.orEmpty()) })
    }

    private fun loadTemplate() {
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
        this.template = template
        subscriptions += Single
            .create<List<FormItem>> { emitter ->
                val items = when {
                    template == null -> {
                        emitter.onError(IllegalStateException("Template not found"))
                        return@create
                    }
                    template.nested != null -> template.nested.asSequence()
                        .map { templateConverter.convert(it) }
                        .toList()
                    template.fields != null -> template.fields.asSequence()
                        .map { fieldConverter.convert(it, fields) }
                        .toList()
                    else -> {
                        emitter.onError(IllegalStateException("Template has no neither nested items nor fields"))
                        return@create
                    }
                }
                emitter.onSuccess(items)
            }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribe(
                { onReady(it) },
                { onError(it) }
            )
    }

    private fun onReady(items: List<FormItem>) {
        this.items = items
        val dataSource = ListDataSource(items)
        adapterPresenter.get().onDataSourceChanged(dataSource)
        view?.contentUpdated()
    }

    private fun onCompleted() {
        router?.leaveScreen(changed = true)
    }

    private fun onError(it: Throwable) {
        throw it
    }

    override fun onBackPressed() {
        navigation -= navigation.last()
        if (navigation.isNotEmpty()) {
            loadTemplate()
        } else {
            router?.leaveScreen(changed = false)
        }
    }

    private fun getGroupId() = navigation.lastOrNull() ?: ID_ROOT

}

private const val KEY_NAVIGATION = "navigation"
private const val KEY_TEMPLATE = "template"
private const val KEY_ITEMS = "items"
