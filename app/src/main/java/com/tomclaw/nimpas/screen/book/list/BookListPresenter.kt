package com.tomclaw.nimpas.screen.book.list

import android.os.Bundle
import android.util.LongSparseArray
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.data_source.ListDataSource
import com.tomclaw.nimpas.screen.book.list.adapter.ItemClickListener
import com.tomclaw.nimpas.storage.Book
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Lazy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

interface BookListPresenter : ItemClickListener {

    fun attachView(view: BookListView)

    fun detachView()

    fun attachRouter(router: BookListRouter)

    fun detachRouter()

    fun saveState(): Bundle

    fun onBackPressed()

    fun onUpdate()

    interface BookListRouter {

        fun showLockScreen()

        fun showBookAddScreen()

        fun leaveScreen()

    }

}

class BookListPresenterImpl(
        private val interactor: BookListInteractor,
        private val adapterPresenter: Lazy<AdapterPresenter>,
        private val bookConverter: BookConverter,
        private val schedulers: SchedulersFactory,
        state: Bundle?
) : BookListPresenter {

    private var view: BookListView? = null
    private var router: BookListPresenter.BookListRouter? = null

    private val subscriptions = CompositeDisposable()

    private val bookIds = LongSparseArray<String>()

    override fun attachView(view: BookListView) {
        this.view = view

        subscriptions += view.navigationClicks().subscribe {
            onBackPressed()
        }
        subscriptions += view.bookAddClicks().subscribe {
            router?.showBookAddScreen()
        }

        loadEntries()
    }

    override fun detachView() {
        subscriptions.clear()
        this.view = null
    }

    override fun attachRouter(router: BookListPresenter.BookListRouter) {
        this.router = router
    }

    override fun detachRouter() {
        this.router = null
    }

    override fun saveState() = Bundle().apply {
    }

    private fun loadEntries() {
        subscriptions += interactor.listBooks()
                .observeOn(schedulers.mainThread())
                .doOnSubscribe { view?.showProgress() }
                .doAfterTerminate { view?.showContent() }
                .subscribe(
                        { onLoaded(it) },
                        { onError(it) }
                )
    }

    private fun onLoaded(books: Map<String, Book>) {
        val items = books.asSequence()
                .sortedBy { it.value.getWriteTime() }
                .map {
                    val item = bookConverter.convert(it.value)
                    bookIds.put(item.id, it.key)
                    item
                }
                .toList()
        val dataSource = ListDataSource(items)
        adapterPresenter.get().onDataSourceChanged(dataSource)
        view?.contentUpdated()
    }

    private fun onError(it: Throwable) {}

    override fun onBackPressed() {
        router?.leaveScreen()
    }

    override fun onUpdate() {
        view?.contentUpdated()
    }

    override fun onItemClick(item: Item) {
        val bookId = bookIds[item.id] ?: return
        subscriptions += interactor.switchBook(bookId)
                .observeOn(schedulers.mainThread())
                .subscribe(
                        { onBookSwitched() },
                        { onError(it) }
                )
    }

    private fun onBookSwitched() {
        router?.showLockScreen()
    }

}
