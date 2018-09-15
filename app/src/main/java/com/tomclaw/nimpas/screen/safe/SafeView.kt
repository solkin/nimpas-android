package com.tomclaw.nimpas.screen.safe

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.View
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.avito.konveyor.blueprint.Item
import com.tomclaw.nimpas.R
import io.reactivex.Observable


interface SafeView {

    fun showProgress()

    fun showContent()

    fun itemClicks(): Observable<Item>

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
        val itemDecoration = DividerItemDecoration(view.context, orientation)
        adapter.setHasStableIds(true)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
        recycler.addItemDecoration(itemDecoration)
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showContent() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun itemClicks(): Observable<Item> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun contentUpdated() {
        adapter.notifyDataSetChanged()
    }

}