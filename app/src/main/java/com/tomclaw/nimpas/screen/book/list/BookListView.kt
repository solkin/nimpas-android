package com.tomclaw.nimpas.screen.book.list

import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.appcompat.widget.Toolbar
import android.view.View
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.jakewharton.rxrelay2.PublishRelay
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.clicks
import io.reactivex.Observable

interface BookListView {

    fun showProgress()

    fun showContent()

    fun contentUpdated()

    fun navigationClicks(): Observable<Unit>

    fun bookAddClicks(): Observable<Unit>

}

class BookListViewImpl(
        view: View,
        private val adapter: SimpleRecyclerAdapter
) : BookListView {

    private val toolbar: Toolbar = view.findViewById(R.id.toolbar)
    private val recycler: androidx.recyclerview.widget.RecyclerView = view.findViewById(R.id.recycler)
    private val bookAddButton: FloatingActionButton = view.findViewById(R.id.book_add_button)

    private val navigationRelay = PublishRelay.create<Unit>()
    private val bookAddRelay = PublishRelay.create<Unit>()

    init {
        toolbar.setTitle(R.string.select_book)
        toolbar.setNavigationOnClickListener {
            navigationRelay.accept(Unit)
        }
        val orientation = VERTICAL
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(view.context, orientation, false)
        adapter.setHasStableIds(true)
        recycler.adapter = adapter
        recycler.layoutManager = layoutManager
        recycler.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recycler.itemAnimator?.changeDuration = DURATION_MEDIUM

        bookAddButton.clicks(bookAddRelay)
    }

    override fun showProgress() {}

    override fun showContent() {}

    override fun contentUpdated() {
        adapter.notifyDataSetChanged()
    }

    override fun navigationClicks(): Observable<Unit> = navigationRelay

    override fun bookAddClicks(): Observable<Unit> {
        return bookAddRelay
    }

}

private const val DURATION_MEDIUM = 300L
