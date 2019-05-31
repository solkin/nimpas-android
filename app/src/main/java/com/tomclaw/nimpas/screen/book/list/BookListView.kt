package com.tomclaw.nimpas.screen.book.list

import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.VERTICAL
import android.support.v7.widget.Toolbar
import android.view.View
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.jakewharton.rxrelay2.PublishRelay
import com.tomclaw.nimpas.R
import io.reactivex.Observable

interface BookListView {

    fun showProgress()

    fun showContent()

    fun contentUpdated()

    fun bookAddClicks(): Observable<Unit>

}

class BookListViewImpl(
        private val view: View,
        private val adapter: SimpleRecyclerAdapter
) : BookListView {

    private val resources = view.resources

    private val toolbar: Toolbar = view.findViewById(R.id.toolbar)
    private val recycler: RecyclerView = view.findViewById(R.id.recycler)
    private val bookAddButton: FloatingActionButton = view.findViewById(R.id.book_add_button)

    private val bookAddRelay = PublishRelay.create<Unit>()

    init {
        toolbar.setTitle(R.string.select_book)
        val orientation = VERTICAL
        val layoutManager = LinearLayoutManager(view.context, orientation, false)
        adapter.setHasStableIds(true)
        recycler.adapter = adapter
        recycler.layoutManager = layoutManager
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.itemAnimator?.changeDuration = DURATION_MEDIUM

        bookAddButton.setOnClickListener { bookAddRelay.accept(Unit) }
    }

    override fun showProgress() {}

    override fun showContent() {}

    override fun contentUpdated() {
        adapter.notifyDataSetChanged()
    }

    override fun bookAddClicks(): Observable<Unit> {
        return bookAddRelay
    }

}

private const val DURATION_MEDIUM = 300L
