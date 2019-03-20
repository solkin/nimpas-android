package com.tomclaw.nimpas.screen.user.list

import android.os.Bundle
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.disposables.CompositeDisposable

interface UserListPresenter {

    fun attachView(view: UserListView)

    fun detachView()

    fun attachRouter(router: UserListRouter)

    fun detachRouter()

    fun saveState(): Bundle

    fun onBackPressed()

    interface UserListRouter {

        fun leaveScreen()

    }

}

class UserListPresenterImpl(
        private val interactor: UserListInteractor,
        private val schedulers: SchedulersFactory,
        state: Bundle?
) : UserListPresenter {

    private var view: UserListView? = null
    private var router: UserListPresenter.UserListRouter? = null

    private val subscriptions = CompositeDisposable()

    override fun attachView(view: UserListView) {
        this.view = view
    }

    override fun detachView() {
        subscriptions.clear()
        this.view = null
    }

    override fun attachRouter(router: UserListPresenter.UserListRouter) {
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
