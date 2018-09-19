package com.tomclaw.nimpas.screen.safe

import android.os.Bundle
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.data_source.ListDataSource
import com.tomclaw.nimpas.journal.GROUP_DEFAULT
import com.tomclaw.nimpas.journal.Record
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

interface SafePresenter {

    fun attachView(view: SafeView)

    fun detachView()

    fun attachRouter(router: SafeRouter)

    fun detachRouter()

    fun saveState(): Bundle

    fun onUpdate()

    interface SafeRouter {

        fun leaveScreen()

    }

}

class SafePresenterImpl(
        private val interactor: SafeInteractor,
        private val adapterPresenter: AdapterPresenter,
        private val recordConverter: RecordConverter,
        private val schedulers: SchedulersFactory,
        state: Bundle?
) : SafePresenter {

    private var view: SafeView? = null
    private var router: SafePresenter.SafeRouter? = null

    private val subscriptions = CompositeDisposable()

    override fun attachView(view: SafeView) {
        this.view = view

        subscriptions += view.itemClicks().subscribe { item ->
            loadRecords(groupId = item.id)
        }

        loadRecords()
    }

    override fun detachView() {
        subscriptions.clear()
        this.view = null
    }

    override fun attachRouter(router: SafePresenter.SafeRouter) {
        this.router = router
    }

    override fun detachRouter() {
        this.router = null
    }

    override fun saveState() = Bundle().apply {}

    private fun loadRecords(groupId: Long = GROUP_DEFAULT) {
        subscriptions += interactor.getRecords(groupId)
                .observeOn(schedulers.mainThread())
                .doOnSubscribe { view?.showProgress() }
                .doAfterTerminate { view?.showContent() }
                .subscribe(
                        { onLoaded(it) },
                        { onError() }
                )
    }

    private fun onLoaded(records: List<Record>) {
        val items = records.map { recordConverter.convert(it) }
        val dataSource = ListDataSource(items)
        adapterPresenter.onDataSourceChanged(dataSource)
        view?.contentUpdated()
    }

    private fun onError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUpdate() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

private const val RECORDS_LIST_KEY = "records_list"