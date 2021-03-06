package com.tomclaw.nimpas.screen.book.importing

import android.net.Uri
import android.os.Bundle
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

interface BookImportPresenter {

    fun attachView(view: BookImportView)

    fun detachView()

    fun attachRouter(router: BookImportRouter)

    fun detachRouter()

    fun saveState(): Bundle

    fun onBackPressed()

    fun onImportBook(uri: Uri)

    interface BookImportRouter {

        fun showLockScreen()

        fun leaveScreen()

    }

}

class BookImportPresenterImpl(
        private val interactor: BookImportInteractor,
        private val schedulers: SchedulersFactory,
        state: Bundle?
) : BookImportPresenter {

    private var view: BookImportView? = null
    private var router: BookImportPresenter.BookImportRouter? = null

    private val subscriptions = CompositeDisposable()

    override fun attachView(view: BookImportView) {
        this.view = view

        subscriptions += view.navigationClicks().subscribe { onBackPressed() }
    }

    override fun detachView() {
        subscriptions.clear()
        this.view = null
    }

    override fun attachRouter(router: BookImportPresenter.BookImportRouter) {
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

    override fun onImportBook(uri: Uri) {
        importBook(uri)
    }

    private fun importBook(uri: Uri) {
        subscriptions += interactor.importBook(uri)
                .observeOn(schedulers.mainThread())
                .subscribe(
                        { onBookImported() },
                        { onBookImportFailed(it) }
                )
    }

    private fun onBookImportFailed(ex: Throwable) {
        // TODO: handle error
        ex.printStackTrace()
    }

    private fun onBookImported() {
        router?.showLockScreen()
    }

}
