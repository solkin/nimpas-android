package com.tomclaw.nimpas.screen.info

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

interface InfoView {

    fun setTitle(title: String)

    fun contentUpdated()

    fun navigationClicks(): Observable<Unit>

    fun editClicks(): Observable<Unit>

    fun deleteClicks(): Observable<Unit>

}

class InfoViewImpl(
        private val view: View,
        private val adapter: SimpleRecyclerAdapter
) : InfoView {

    private val context: Context = view.context
    private val toolbar: Toolbar = view.findViewById(R.id.toolbar)
    private val recycler: androidx.recyclerview.widget.RecyclerView = view.findViewById(R.id.recycler)

    private val navigationRelay = PublishRelay.create<Unit>()
    private val editRelay = PublishRelay.create<Unit>()
    private val deleteRelay = PublishRelay.create<Unit>()

    init {
        toolbar.setTitle(R.string.info)
        toolbar.inflateMenu(R.menu.info)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_edit -> editRelay.accept(Unit)
                R.id.menu_delete -> deleteRelay.accept(Unit)
            }
            true
        }
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

    override fun contentUpdated() {
        adapter.notifyDataSetChanged()
    }

    override fun navigationClicks(): Observable<Unit> = navigationRelay

    override fun editClicks(): Observable<Unit> = editRelay

    override fun deleteClicks(): Observable<Unit> = deleteRelay

}

private const val DURATION_MEDIUM = 300L
