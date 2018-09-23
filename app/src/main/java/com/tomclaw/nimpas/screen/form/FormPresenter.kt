package com.tomclaw.nimpas.screen.form

import android.os.Bundle
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.disposables.CompositeDisposable

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
        private val schedulers: SchedulersFactory,
        state: Bundle?
) : FormPresenter {

    private var view: FormView? = null
    private var router: FormPresenter.FormRouter? = null

    private val subscriptions = CompositeDisposable()

    override fun attachView(view: FormView) {
        this.view = view
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

    override fun onBackPressed() {
        router?.leaveScreen()
    }

}
