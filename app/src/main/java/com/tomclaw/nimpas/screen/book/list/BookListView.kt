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

    fun createClicks(): Observable<Unit>

}

class BookListViewImpl(
        private val view: View,
        private val adapter: SimpleRecyclerAdapter
) : BookListView {

    private val resources = view.resources

    private val toolbar: Toolbar = view.findViewById(R.id.toolbar)
    private val recycler: RecyclerView = view.findViewById(R.id.recycler)
    private val createButton: FloatingActionButton = view.findViewById(R.id.create_button)

    private val createRelay = PublishRelay.create<Unit>()

    init {
        toolbar.setTitle(R.string.select_book)
        val orientation = VERTICAL
        val layoutManager = LinearLayoutManager(view.context, orientation, false)
        adapter.setHasStableIds(true)
        recycler.adapter = adapter
        recycler.layoutManager = layoutManager
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.itemAnimator?.changeDuration = DURATION_MEDIUM

        createButton.setOnClickListener { createRelay.accept(Unit) }
    }

    override fun showProgress() {}

    override fun showContent() {}

    override fun contentUpdated() {
        adapter.notifyDataSetChanged()
    }

    override fun createClicks(): Observable<Unit> {
        return createRelay
    }

}

private const val DURATION_MEDIUM = 300L
