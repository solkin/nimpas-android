package com.tomclaw.nimpas.screen.book.add

import android.os.Bundle
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

interface BookAddPresenter {

    fun attachView(view: BookAddView)

    fun detachView()

    fun attachRouter(router: BookAddRouter)

    fun detachRouter()

    fun saveState(): Bundle

    fun onBackPressed()

    interface BookAddRouter {

        fun showLockScreen()

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

    private var title: String = state?.getString(KEY_TITLE).orEmpty()
    private var keyword: String = state?.getString(KEY_KEYWORD).orEmpty()

    private val subscriptions = CompositeDisposable()

    override fun attachView(view: BookAddView) {
        this.view = view

        subscriptions += view.navigationClicks().subscribe { onBackPressed() }
        subscriptions += view.titleChanges().subscribe { title = it }
        subscriptions += view.keywordChanges().subscribe { keyword = it }
        subscriptions += view.bookAddClicks().subscribe { addBook() }
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

    private fun addBook() {
        // TODO: check for field filled correctly
        subscriptions += interactor.createBook(title, keyword)
                .observeOn(schedulers.mainThread())
                .subscribe(
                        { onBookCreated() },
                        { onBookCreationFailed(it) }
                )
    }

    private fun onBookCreationFailed(ex: Throwable) {
        // TODO: handle error
        ex.printStackTrace()
    }

    private fun onBookCreated() {
        router?.showLockScreen()
    }

}

private const val KEY_TITLE = "title"
private const val KEY_KEYWORD = "keyword"
