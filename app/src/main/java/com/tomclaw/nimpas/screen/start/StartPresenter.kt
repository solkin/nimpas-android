package com.tomclaw.nimpas.screen.start

import android.os.Bundle
import com.tomclaw.nimpas.storage.BookLockedException
import com.tomclaw.nimpas.storage.NoActiveBookException
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

interface StartPresenter {

    fun attachView(view: StartView)

    fun detachView()

    fun attachRouter(router: StartRouter)

    fun detachRouter()

    fun saveState(): Bundle

    fun onBackPressed()

    interface StartRouter {

        fun showBookListScreen()

        fun showLockScreen()

        fun leaveScreen()

    }

}

class StartPresenterImpl(
        private val interactor: StartInteractor,
        private val schedulers: SchedulersFactory,
        state: Bundle?
) : StartPresenter {

    private var view: StartView? = null
    private var router: StartPresenter.StartRouter? = null

    private val subscriptions = CompositeDisposable()

    override fun attachView(view: StartView) {
        this.view = view

        checkBook()
    }

    override fun detachView() {
        subscriptions.clear()
        this.view = null
    }

    override fun attachRouter(router: StartPresenter.StartRouter) {
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

    private fun checkBook() {
        subscriptions += interactor.check()
                .observeOn(schedulers.mainThread())
                .subscribe(
                        { onUnlocked() },
                        { onError(it) }
                )
    }

    private fun onError(ex: Throwable?) {
        when (ex) {
            is BookLockedException -> router?.showLockScreen()
            is NoActiveBookException -> router?.showBookListScreen()
        }
    }

    private fun onUnlocked() {
    }

}
