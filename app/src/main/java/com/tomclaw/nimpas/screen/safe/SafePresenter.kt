package com.tomclaw.nimpas.screen.safe

import android.os.Bundle
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.data_source.ListDataSource
import com.tomclaw.nimpas.journal.GROUP_DEFAULT
import com.tomclaw.nimpas.journal.JournalImpl
import com.tomclaw.nimpas.journal.Record
import com.tomclaw.nimpas.journal.TYPE_GROUP
import com.tomclaw.nimpas.screen.safe.adapter.ItemClickListener
import com.tomclaw.nimpas.templates.TemplateRepository
import com.tomclaw.nimpas.util.SchedulersFactory
import dagger.Lazy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

interface SafePresenter : ItemClickListener {

    fun attachView(view: SafeView)

    fun detachView()

    fun attachRouter(router: SafeRouter)

    fun detachRouter()

    fun saveState(): Bundle

    fun onBackPressed()

    fun onUpdate()

    interface SafeRouter {

        fun showFormScreen(templateId: Long, groupId: Long)

        fun showLockScreen()

        fun leaveScreen()

    }

}

@Suppress("SuspiciousCollectionReassignment")
class SafePresenterImpl(
        private val interactor: SafeInteractor,
        private val adapterPresenter: Lazy<AdapterPresenter>,
        private val recordConverter: RecordConverter,
        private val templateRepository: TemplateRepository,
        private val schedulers: SchedulersFactory,
        state: Bundle?
) : SafePresenter {

    private var view: SafeView? = null
    private var router: SafePresenter.SafeRouter? = null

    private val subscriptions = CompositeDisposable()

    private var navigation: Set<Long> = state?.getLongArray(KEY_NAVIGATION)?.toMutableSet()
            ?: mutableSetOf()

    override fun attachView(view: SafeView) {
        this.view = view

        subscriptions += view.buttonClicks().subscribe {
            onShowCreateMenu()
        }

        subscriptions += view.createClicks().subscribe { templateId ->
            val groupId = getGroupId()
            router?.showFormScreen(templateId, groupId)
        }

        loadRecords(groupId = getGroupId())
    }

    private fun onShowCreateMenu() {
        subscriptions += templateRepository.getTemplates()
                .observeOn(schedulers.mainThread())
                .subscribe {
                    val items = it.map { template ->
                        MenuItem(
                                id = template.id,
                                title = template.title.orEmpty(),
                                icon = template.icon
                        )
                    }
                    view?.showCreateDialog(items)
                }
    }

    override fun detachView() {
        subscriptions.clear()
        this.view = null
    }

    override fun attachRouter(router: SafePresenter.SafeRouter) {
        this.router = router
    }

    override fun detachRouter() {
        this.router = null
    }

    override fun saveState() = Bundle().apply {
        putLongArray(KEY_NAVIGATION, navigation.toLongArray())
    }

    private fun loadRecords(groupId: Long = GROUP_DEFAULT) {
        subscriptions += interactor.getRecords(groupId)
                .observeOn(schedulers.mainThread())
                .doOnSubscribe { view?.showProgress() }
                .doAfterTerminate { view?.showContent() }
                .subscribe(
                        { onLoaded(it) },
                        { onError(it) }
                )
    }

    private fun onLoaded(records: List<Record>) {
        val items = records.asSequence()
                .sortedWith(compareBy({ it.type == TYPE_GROUP }, { it.time }))
                .map { recordConverter.convert(it) }
                .toList()
        val dataSource = ListDataSource(items)
        adapterPresenter.get().onDataSourceChanged(dataSource)
        view?.contentUpdated()
    }

    private fun onError(it: Throwable) {
        when (it) {
            is JournalImpl.JournalLockedException -> router?.showLockScreen()
        }
    }

    override fun onBackPressed() {
        if (navigation.isNotEmpty()) {
            navigation -= navigation.last()
            loadRecords(groupId = getGroupId())
        } else {
            router?.leaveScreen()
        }
    }

    override fun onUpdate() {
        loadRecords(groupId = getGroupId())
        view?.contentUpdated()
    }

    override fun onItemClick(item: Item) {
        navigation += item.id
        loadRecords(item.id)
    }

    private fun getGroupId() = navigation.lastOrNull() ?: GROUP_DEFAULT

}

private const val KEY_NAVIGATION = "navigation"
