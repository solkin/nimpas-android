package com.tomclaw.nimpas.screen.user.list

import android.os.Bundle
import android.util.LongSparseArray
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.data_source.ListDataSource
import com.tomclaw.nimpas.screen.user.list.adapter.ItemClickListener
import com.tomclaw.nimpas.storage.Book
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Lazy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

interface UserListPresenter : ItemClickListener {

    fun attachView(view: UserListView)

    fun detachView()

    fun attachRouter(router: UserListRouter)

    fun detachRouter()

    fun saveState(): Bundle

    fun onBackPressed()

    fun onUpdate()

    interface UserListRouter {

        fun showLockScreen()

        fun showUserAddScreen()

        fun leaveScreen()

    }

}

class UserListPresenterImpl(
        private val interactor: UserListInteractor,
        private val adapterPresenter: Lazy<AdapterPresenter>,
        private val bookConverter: BookConverter,
        private val schedulers: SchedulersFactory,
        state: Bundle?
) : UserListPresenter {

    private var view: UserListView? = null
    private var router: UserListPresenter.UserListRouter? = null

    private val subscriptions = CompositeDisposable()

    private val bookIds = LongSparseArray<String>()

    override fun attachView(view: UserListView) {
        this.view = view

        loadEntries()
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
