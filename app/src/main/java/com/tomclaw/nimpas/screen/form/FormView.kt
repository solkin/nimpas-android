package com.tomclaw.nimpas.screen.form

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.view.View
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.jakewharton.rxrelay2.PublishRelay
import com.tomclaw.nimpas.R
import io.reactivex.Observable


interface FormView {

    fun setTitle(title: String)

    fun showProgress()

    fun showContent()

    fun contentUpdated()

    fun navigationClicks(): Observable<Unit>

}

class FormViewImpl(
        private val view: View,
        private val adapter: SimpleRecyclerAdapter
) : FormView {

    private val context: Context = view.context
    private val toolbar: Toolbar = view.findViewById(R.id.toolbar)
    private val recycler: androidx.recyclerview.widget.RecyclerView = view.findViewById(R.id.recycler)

    private val navigationRelay = PublishRelay.create<Unit>()

    init {
        toolbar.setTitle(R.string.create)
        toolbar.setNavigationOnClickListener {
            navigationRelay.accept(Unit)
        }
        val orientation = androidx.recyclerview.widget.RecyclerView.VERTICAL
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(view.context, orientation, false)
        adapter.setHasStableIds(true)
        recycler.adapter = adapter
        recycler.layoutManager = layoutManager
        recycler.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recycler.itemAnimator?.changeDuration = DURATION_MEDIUM
        ContextCompat.getDrawable(context, R.drawable.form_divider)?.let {
            val dividerDecoration = androidx.recyclerview.widget.DividerItemDecoration(recycler.context, layoutManager.orientation)
            dividerDecoration.setDrawable(it)
            recycler.addItemDecoration(dividerDecoration)
        }
    }

    override fun setTitle(title: String) {
        toolbar.title = title
    }

    override fun showProgress() {}

    override fun showContent() {}

    override fun contentUpdated() {
        adapter.notifyDataSetChanged()
    }

    override fun navigationClicks(): Observable<Unit> = navigationRelay

}

private const val DURATION_MEDIUM = 300L
