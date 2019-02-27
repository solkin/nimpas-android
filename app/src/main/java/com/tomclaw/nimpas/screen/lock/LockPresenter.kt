package com.tomclaw.nimpas.screen.lock

import android.os.Bundle
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

interface LockPresenter {

    fun attachView(view: LockView)

    fun detachView()

    fun attachRouter(router: LockRouter)

    fun detachRouter()

    fun saveState(): Bundle

    fun onBackPressed()

    interface LockRouter {

        fun leaveScreen(isUnlocked: Boolean)

    }

}

class LockPresenterImpl(
        private val interactor: LockInteractor,
        private val schedulers: SchedulersFactory,
        state: Bundle?
) : LockPresenter {

    private var view: LockView? = null
    private var router: LockPresenter.LockRouter? = null

    private var keyword: String = state?.getString(KEY_KEYWORD).orEmpty()

    private val subscriptions = CompositeDisposable()

    override fun attachView(view: LockView) {
        this.view = view

        subscriptions += view.keywordChanges().subscribe { keyword = it }
        subscriptions += view.unlockClicks().subscribe { unlockJournal() }
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
        putString(KEY_KEYWORD, keyword)
    }

    override fun onBackPressed() {
        router?.leaveScreen(isUnlocked = false)
    }

    private fun unlockJournal() {
        subscriptions += interactor.unlock(keyword)
                .observeOn(schedulers.mainThread())
                .subscribe(
                        { onJournalUnlocked() },
                        { onUnlockFailed(it) }
                )
    }

    private fun onJournalUnlocked() {
        // TODO: show animation
        router?.leaveScreen(isUnlocked = true)
    }

    private fun onUnlockFailed(ex: Throwable) {
        // TODO: show animation
        ex.printStackTrace()
        view?.showUnlockError()
    }

}

private const val KEY_KEYWORD = "keyword"
