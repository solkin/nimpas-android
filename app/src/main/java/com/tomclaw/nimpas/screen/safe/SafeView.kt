package com.tomclaw.nimpas.screen.safe

import android.graphics.drawable.BitmapDrawable
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.VERTICAL
import android.support.v7.widget.Toolbar
import android.view.View
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.caverock.androidsvg.SVG
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder
import com.jakewharton.rxrelay2.PublishRelay
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.dpToPx
import com.tomclaw.nimpas.util.toBitmap
import io.reactivex.Observable


interface SafeView {

    fun showProgress()

    fun showContent()

    fun contentUpdated()

    fun showCreateDialog(items: List<MenuItem>)

    fun buttonClicks(): Observable<Unit>

    fun createClicks(): Observable<Long>

    fun undoClicks(): Observable<Long>

    fun showUndoMessage(id: Long, delay: Long, message: String)

}

class SafeViewImpl(
        private val view: View,
        private val adapter: SimpleRecyclerAdapter
) : SafeView {

    private val resources = view.resources

    private val toolbar: Toolbar = view.findViewById(R.id.toolbar)
    private val recycler: RecyclerView = view.findViewById(R.id.recycler)
    private val createButton: FloatingActionButton = view.findViewById(R.id.create_button)

    private val buttonRelay = PublishRelay.create<Unit>()
    private val createRelay = PublishRelay.create<Long>()
    private val undoRelay = PublishRelay.create<Long>()

    init {
        toolbar.setTitle(R.string.app_name)
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

    override fun undoClicks(): Observable<Long> {
        return undoRelay
    }

    override fun showCreateDialog(items: List<MenuItem>) {
        BottomSheetBuilder(view.context, R.style.AppTheme_BottomSheetDialog)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setIconTintColorResource(R.color.color_grey)
                .apply {
                    items.forEachIndexed { index, item ->
                        val picture = SVG.getFromString(item.icon).renderToPicture()
                        val bitmap = picture.toBitmap(
                                bitmapWidth = dpToPx(picture.width, resources),
                                bitmapHeight = dpToPx(picture.height, resources)
                        )
                        val icon = BitmapDrawable(view.resources, bitmap)
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

    override fun showUndoMessage(id: Long, delay: Long, message: String) {
        Snackbar.make(recycler, message, delay.toInt())
                .setAction(R.string.undo) { undoRelay.accept(id) }
                .show()
    }

}

private const val DURATION_MEDIUM = 300L
