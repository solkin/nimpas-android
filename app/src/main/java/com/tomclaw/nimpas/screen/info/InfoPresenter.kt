package com.tomclaw.nimpas.screen.info

import android.os.Bundle
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.data_source.ListDataSource
import com.tomclaw.nimpas.journal.Record
import com.tomclaw.nimpas.screen.info.adapter.InfoItem
import com.tomclaw.nimpas.screen.info.converter.FieldConverter
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Lazy
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

interface InfoPresenter {

    fun attachView(view: InfoView)

    fun detachView()

    fun attachRouter(router: InfoRouter)

    fun detachRouter()

    fun saveState(): Bundle

    fun onBackPressed()

    fun onUpdate()

    interface InfoRouter {

        fun showEditScreen(record: Record)

        fun leaveScreen(changed: Boolean)

    }

}

class InfoPresenterImpl(
        private val recordId: Long,
        private val interactor: InfoInteractor,
        private val adapterPresenter: Lazy<AdapterPresenter>,
        private val fieldConverter: FieldConverter,
        private val schedulers: SchedulersFactory,
        state: Bundle?
) : InfoPresenter {

    private var view: InfoView? = null
    private var router: InfoPresenter.InfoRouter? = null

    private val subscriptions = CompositeDisposable()

    private var record: Record? = state?.getParcelable(KEY_RECORD)
    private var items: List<InfoItem>? = state?.getParcelableArrayList(KEY_ITEMS)
    private var changed: Boolean = state?.getBoolean(KEY_CHANGED) ?: false

    override fun attachView(view: InfoView) {
        this.view = view

        subscriptions += view.navigationClicks().subscribe {
            onBackPressed()
        }
        subscriptions += view.editClicks().subscribe {
            record?.let { router?.showEditScreen(it) }
        }
        subscriptions += view.deleteClicks().subscribe {
        }

        record?.let { onLoaded(it) } ?: loadRecord()
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

    private fun loadRecord() {
        subscriptions += interactor.getRecord(recordId)
                .observeOn(schedulers.mainThread())
                .subscribe(
                        { onLoaded(it) },
                        { onError(it) }
                )
    }

    private fun onLoaded(record: Record) {
        changed = this.record?.let { it.time == record.time }?.takeIf { true }  ?: changed
        this.record = record
        subscriptions += Single
                .create<List<InfoItem>> { emitter ->
                    val template = record.template
                    val items = when {
                        template.fields != null -> template.fields.asSequence()
                                .map { fieldConverter.convert(field = it, params = record.fields) }
                                .filterNotNull()
                                .toList()
                        else -> {
                            emitter.onError(IllegalStateException("Template has no fields"))
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

    private fun onReady(items: List<InfoItem>) {
        this.items = items
        val dataSource = ListDataSource(items)
        adapterPresenter.get().onDataSourceChanged(dataSource)
        view?.contentUpdated()
        view?.setTitle(record?.template?.title.orEmpty())
    }

    private fun onError(it: Throwable) {}

    override fun onBackPressed() {
        router?.leaveScreen(changed)
    }

    override fun onUpdate() {
        loadRecord()
        view?.contentUpdated()
    }

}

private const val KEY_RECORD = "record"
private const val KEY_ITEMS = "items"
private const val KEY_CHANGED = "changed"
