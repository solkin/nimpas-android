package com.tomclaw.nimpas.screen.book.add

import android.os.Bundle
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.disposables.CompositeDisposable

interface BookAddPresenter {

    fun attachView(view: BookAddView)

    fun detachView()

    fun attachRouter(router: BookAddRouter)

    fun detachRouter()

    fun saveState(): Bundle

    fun onBackPressed()

    interface BookAddRouter {

        fun leaveScreen()

    }

}

class BookAddPresenterImpl(
        private val interactor: BookAddInteractor,
        private val schedulers: SchedulersFactory,
        state: Bundle?
) : BookAddPresenter {

    private var view: BookAddView? = null
    private var router: BookAddPresenter.BookAddRouter? = null

    private val subscriptions = CompositeDisposable()

    override fun attachView(view: BookAddView) {
        this.view = view
    }

    override fun detachView() {
        subscriptions.clear()
        this.view = null
    }

    override fun attachRouter(router: BookAddPresenter.BookAddRouter) {
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
