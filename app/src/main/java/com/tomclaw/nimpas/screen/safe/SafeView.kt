package com.tomclaw.nimpas.screen.safe

import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.View
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGDrawable
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder
import com.jakewharton.rxrelay2.PublishRelay
import com.tomclaw.nimpas.R
import io.reactivex.Observable


interface SafeView {

    fun showProgress()

    fun showContent()

    fun contentUpdated()

    fun showCreateDialog(items: List<MenuItem>)

    fun buttonClicks(): Observable<Unit>

    fun createClicks(): Observable<Long>

}

class SafeViewImpl(
        private val view: View,
        private val adapter: SimpleRecyclerAdapter
) : SafeView {

    private val recycler: RecyclerView = view.findViewById(R.id.recycler)
    private val createButton: FloatingActionButton = view.findViewById(R.id.create_button)

    private val buttonRelay = PublishRelay.create<Unit>()
    private val createRelay = PublishRelay.create<Long>()

    init {
        val orientation = VERTICAL
        val layoutManager = LinearLayoutManager(view.context, orientation, false)
        adapter.setHasStableIds(true)
        recycler.adapter = adapter
        recycler.layoutManager = layoutManager
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.itemAnimator?.changeDuration = DURATION_MEDIUM

        createButton.setOnClickListener { buttonRelay.accept(Unit) }
    }

    override fun showProgress() {}

    override fun showContent() {}

    override fun contentUpdated() {
        adapter.notifyDataSetChanged()
    }

    override fun buttonClicks(): Observable<Unit> {
        return buttonRelay
    }

    override fun createClicks(): Observable<Long> {
        return createRelay
    }

    override fun showCreateDialog(items: List<MenuItem>) {
        BottomSheetBuilder(view.context, R.style.AppTheme_BottomSheetDialog)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setIconTintColorResource(R.color.color_grey)
                .apply {
                    items.forEachIndexed { index, item ->
                        val icon = SVGDrawable(SVG.getFromString(item.icon))
                        addItem(index, item.title, icon)
                    }
                }
                .setItemClickListener {
                    val menuItem = items[it.itemId]
                    createRelay.accept(menuItem.id)
                }
                .createDialog()
                .show()
    }

}

private const val DURATION_MEDIUM = 300L
