package com.tomclaw.nimpas.screen.safe

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.View
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.tomclaw.nimpas.R


interface SafeView {

    fun showProgress()

    fun showContent()

    fun contentUpdated()

}

class SafeViewImpl(
        private val view: View,
        private val adapter: SimpleRecyclerAdapter
) : SafeView {

    private val recycler: RecyclerView = view.findViewById(R.id.recycler)

    init {
        val orientation = VERTICAL
        val layoutManager = LinearLayoutManager(view.context, orientation, false)
        adapter.setHasStableIds(true)
        recycler.adapter = adapter
        recycler.layoutManager = layoutManager
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.itemAnimator?.changeDuration = DURATION_MEDIUM
    }

    override fun showProgress() {

    }

    override fun showContent() {

    }

    override fun contentUpdated() {
        adapter.notifyDataSetChanged()
    }

}

private const val DURATION_MEDIUM = 300L
