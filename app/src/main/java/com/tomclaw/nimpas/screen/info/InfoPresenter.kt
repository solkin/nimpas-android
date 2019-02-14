package com.tomclaw.nimpas.screen.info

import android.os.Bundle
import com.avito.konveyor.adapter.AdapterPresenter
import com.tomclaw.nimpas.screen.info.adapter.InfoItem
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
        private val interactor: InfoInteractor,
        private val adapterPresenter: Lazy<AdapterPresenter>,
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

    override fun onBackPressed() {
        router?.leaveScreen()
    }

}

private const val KEY_ITEMS = "items"
