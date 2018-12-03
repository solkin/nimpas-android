package com.tomclaw.nimpas.screen.form

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.tomclaw.nimpas.R

interface FormView {

    fun showProgress()

    fun showContent()

}

class FormViewImpl(
        private val view: View,
        private val adapter: SimpleRecyclerAdapter
) : FormView {

    private val recycler: RecyclerView = view.findViewById(R.id.recycler)

    init {
        val orientation = RecyclerView.VERTICAL
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

}

private const val DURATION_MEDIUM = 300L
