package com.tomclaw.nimpas.screen.user.add

import android.os.Bundle
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.disposables.CompositeDisposable

interface UserAddPresenter {

    fun attachView(view: UserAddView)

    fun detachView()

    fun attachRouter(router: UserAddRouter)

    fun detachRouter()

    fun saveState(): Bundle

    fun onBackPressed()

    interface UserAddRouter {

        fun leaveScreen()

    }

}

class UserAddPresenterImpl(
        private val interactor: UserAddInteractor,
        private val schedulers: SchedulersFactory,
        state: Bundle?
) : UserAddPresenter {

    private var view: UserAddView? = null
    private var router: UserAddPresenter.UserAddRouter? = null

    private val subscriptions = CompositeDisposable()

    override fun attachView(view: UserAddView) {
        this.view = view
    }

    override fun detachView() {
        subscriptions.clear()
        this.view = null
    }

    override fun attachRouter(router: UserAddPresenter.UserAddRouter) {
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
