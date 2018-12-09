package com.tomclaw.nimpas.screen.form

import android.os.Bundle
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.data_source.ListDataSource
import com.tomclaw.nimpas.screen.form.model.Widget
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

    override fun attachView(view: FormView) {
        this.view = view

        loadWidgets()
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
    }

    private fun loadWidgets() {
//        subscriptions += interactor.getWidgets()
//                .observeOn(schedulers.mainThread())
//                .doOnSubscribe { view?.showProgress() }
//                .doAfterTerminate { view?.showContent() }
//                .subscribe(
//                        { onLoaded(it) },
//                        { onError(it) }
//                )
    }

    private fun onLoaded(records: List<Widget>) {
        val items = records.asSequence()
                .map { widgetConverter.convert(it) }
                .toList()
        val dataSource = ListDataSource(items)
        adapterPresenter.get().onDataSourceChanged(dataSource)
        view?.contentUpdated()
    }

    private fun onError(it: Throwable) {}

    override fun onBackPressed() {
        router?.leaveScreen()
    }

}
