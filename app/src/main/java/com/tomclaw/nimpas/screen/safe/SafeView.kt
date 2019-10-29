package com.tomclaw.nimpas.screen.safe

import android.graphics.drawable.BitmapDrawable
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.appcompat.widget.Toolbar
import android.view.View
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.caverock.androidsvg.SVG
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder
import com.jakewharton.rxrelay2.PublishRelay
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.clicks
import com.tomclaw.nimpas.util.dpToPx
import com.tomclaw.nimpas.util.toBitmap
import io.reactivex.Observable


interface SafeView {

    fun showProgress()

    fun showContent()

    fun contentUpdated()

    fun showCreateDialog(items: List<MenuItem>)

    fun exportClicks(): Observable<Unit>

    fun lockClicks(): Observable<Unit>

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
    private val recycler: androidx.recyclerview.widget.RecyclerView = view.findViewById(R.id.recycler)
    private val coordinator: androidx.coordinatorlayout.widget.CoordinatorLayout = view.findViewById(R.id.coordinator)
    private val createButton: FloatingActionButton = view.findViewById(R.id.create_button)

    private val exportRelay = PublishRelay.create<Unit>()
    private val lockRelay = PublishRelay.create<Unit>()
    private val buttonRelay = PublishRelay.create<Unit>()
    private val createRelay = PublishRelay.create<Long>()
    private val undoRelay = PublishRelay.create<Long>()

    init {
        toolbar.setTitle(R.string.app_name)
        toolbar.inflateMenu(R.menu.safe)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_export -> exportRelay.accept(Unit)
                R.id.menu_lock -> lockRelay.accept(Unit)
            }
            true
        }
        val orientation = VERTICAL
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(view.context, orientation, false)
        adapter.setHasStableIds(true)
        recycler.adapter = adapter
        recycler.layoutManager = layoutManager
        recycler.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recycler.itemAnimator?.changeDuration = DURATION_MEDIUM

        createButton.clicks(buttonRelay)
    }

    override fun showProgress() {}

    override fun showContent() {}

    override fun contentUpdated() {
        adapter.notifyDataSetChanged()
    }

    override fun exportClicks(): Observable<Unit> = exportRelay

    override fun lockClicks(): Observable<Unit> = lockRelay

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
        Snackbar.make(coordinator, message, delay.toInt())
                .setAction(R.string.undo) { undoRelay.accept(id) }
                .show()
    }

}

private const val DURATION_MEDIUM = 300L
