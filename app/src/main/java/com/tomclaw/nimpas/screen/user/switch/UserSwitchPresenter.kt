package com.tomclaw.nimpas.screen.user.switch

import android.os.Bundle
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.disposables.CompositeDisposable

interface UserSwitchPresenter {

    fun attachView(view: UserSwitchView)

    fun detachView()

    fun attachRouter(router: UserSwitchRouter)

    fun detachRouter()

    fun saveState(): Bundle

    fun onBackPressed()

    interface UserSwitchRouter {

        fun leaveScreen()

    }

}

class UserSwitchPresenterImpl(
        private val interactor: UserSwitchInteractor,
        private val schedulers: SchedulersFactory,
        state: Bundle?
) : UserSwitchPresenter {

    private var view: UserSwitchView? = null
    private var router: UserSwitchPresenter.UserSwitchRouter? = null

    private val subscriptions = CompositeDisposable()

    override fun attachView(view: UserSwitchView) {
        this.view = view
    }

    override fun detachView() {
        subscriptions.clear()
        this.view = null
    }

    override fun attachRouter(router: UserSwitchPresenter.UserSwitchRouter) {
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
