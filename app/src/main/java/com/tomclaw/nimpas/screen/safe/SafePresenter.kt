package com.tomclaw.nimpas.screen.safe

import android.os.Bundle
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.data_source.ListDataSource
import com.tomclaw.nimpas.screen.safe.adapter.card.CardItem
import com.tomclaw.nimpas.screen.safe.adapter.group.GroupItem
import com.tomclaw.nimpas.screen.safe.adapter.note.NoteItem
import com.tomclaw.nimpas.screen.safe.adapter.pass.PassItem
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.disposables.CompositeDisposable

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

        val items = listOf<Item>(
                GroupItem(1, "Group title"),
                PassItem(2, 0, "Pass Title", "Subtitle"),
                CardItem(3, 0, "Card Title", "1234 5678 9012 3456"),
                NoteItem(4, "Note Title", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."),
                NoteItem(5, "Note Title", "Lorem ipsum dolor sit amet.")
        )
        val dataSource = ListDataSource(items)
        adapterPresenter.onDataSourceChanged(dataSource)
        view.contentUpdated()
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

    override fun onUpdate() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}