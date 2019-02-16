package com.tomclaw.nimpas.screen.info

import android.os.Bundle
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.data_source.ListDataSource
import com.tomclaw.nimpas.journal.Record
import com.tomclaw.nimpas.screen.info.adapter.InfoItem
import com.tomclaw.nimpas.screen.info.converter.FieldConverter
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Lazy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

interface InfoPresenter {

    fun attachView(view: InfoView)

    fun detachView()

    fun attachRouter(router: InfoRouter)

    fun detachRouter()

    fun saveState(): Bundle

    fun onBackPressed()

    interface InfoRouter {

        fun leaveScreen()

    }

}

class InfoPresenterImpl(
        private val record: Record,
        private val interactor: InfoInteractor,
        private val adapterPresenter: Lazy<AdapterPresenter>,
        private val fieldConverter: FieldConverter,
        private val schedulers: SchedulersFactory,
        state: Bundle?
) : InfoPresenter {

    private var view: InfoView? = null
    private var router: InfoPresenter.InfoRouter? = null

    private val subscriptions = CompositeDisposable()

    private var items: List<InfoItem>? = state?.getParcelableArrayList(KEY_ITEMS)

    override fun attachView(view: InfoView) {
        this.view = view

        subscriptions += view.navigationClicks().subscribe {
            onBackPressed()
        }

        prepare()
    }

    override fun detachView() {
        subscriptions.clear()
        this.view = null
    }

    override fun attachRouter(router: InfoPresenter.InfoRouter) {
        this.router = router
    }

    override fun detachRouter() {
        this.router = null
    }

    override fun saveState(): Bundle = Bundle().apply {
        putParcelableArrayList(KEY_ITEMS, ArrayList(items.orEmpty()))
    }

    private fun prepare() {
        val template = record.template
        val items = when {
            template.fields != null -> template.fields.asSequence()
                    .filter { it.key.isNullOrEmpty() || !record.fields[it.key].isNullOrBlank() }
                    .map { fieldConverter.convert(field = it, params = record.fields) }
                    .filterNotNull()
                    .toList()
            else -> throw IllegalStateException("Template has no fields")
        }
        this.items = items
        val dataSource = ListDataSource(items)
        adapterPresenter.get().onDataSourceChanged(dataSource)
        view?.contentUpdated()
    }

    override fun onBackPressed() {
        router?.leaveScreen()
    }

}

private const val KEY_ITEMS = "items"
