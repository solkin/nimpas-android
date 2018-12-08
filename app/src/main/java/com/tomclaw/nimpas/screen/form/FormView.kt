package com.tomclaw.nimpas.screen.form

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.tomclaw.nimpas.R


interface FormView {

    fun showProgress()

    fun showContent()

    fun contentUpdated()

}

class FormViewImpl(
        private val view: View,
        private val adapter: SimpleRecyclerAdapter
) : FormView {

    private val context: Context = view.context
    private val recycler: RecyclerView = view.findViewById(R.id.recycler)

    init {
        val orientation = RecyclerView.VERTICAL
        val layoutManager = LinearLayoutManager(view.context, orientation, false)
        adapter.setHasStableIds(true)
        recycler.adapter = adapter
        recycler.layoutManager = layoutManager
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.itemAnimator?.changeDuration = DURATION_MEDIUM
        ContextCompat.getDrawable(context, R.drawable.form_divider)?.let {
            val dividerDecoration = DividerItemDecoration(recycler.context, layoutManager.orientation)
            dividerDecoration.setDrawable(it)
            recycler.addItemDecoration(dividerDecoration)
        }

    }

    override fun showProgress() {}

    override fun showContent() {}

    override fun contentUpdated() {
        adapter.notifyDataSetChanged()
    }

}

private const val DURATION_MEDIUM = 300L
