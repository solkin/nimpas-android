package com.tomclaw.nimpas.screen.lock

import android.os.Bundle
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.disposables.CompositeDisposable

interface LockPresenter {

    fun attachView(view: LockView)

    fun detachView()

    fun attachRouter(router: LockRouter)

    fun detachRouter()

    fun saveState(): Bundle

    fun onBackPressed()

    interface LockRouter {

        fun leaveScreen()

    }

}

class LockPresenterImpl(
        private val interactor: LockInteractor,
        private val schedulers: SchedulersFactory,
        state: Bundle?
) : LockPresenter {

    private var view: LockView? = null
    private var router: LockPresenter.LockRouter? = null

    private val subscriptions = CompositeDisposable()

    override fun attachView(view: LockView) {
        this.view = view
    }

    override fun detachView() {
        subscriptions.clear()
        this.view = null
    }

    override fun attachRouter(router: LockPresenter.LockRouter) {
        this.router = router
    }

    override fun detachRouter() {
        this.router = null
    }

    override fun saveState() = Bundle().apply {
    }

    override fun onBackPressed() {
        router?.leaveScreen()
    }

}
