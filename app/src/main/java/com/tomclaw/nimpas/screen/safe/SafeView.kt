package com.tomclaw.nimpas.screen.safe

import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.View
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder
import com.jakewharton.rxrelay2.PublishRelay
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.journal.TYPE_CARD
import com.tomclaw.nimpas.journal.TYPE_GROUP
import com.tomclaw.nimpas.journal.TYPE_NOTE
import com.tomclaw.nimpas.journal.TYPE_PASSWORD
import io.reactivex.Observable


interface SafeView {

    fun showProgress()

    fun showContent()

    fun contentUpdated()

    fun createClicks(): Observable<Int>

}

class SafeViewImpl(
        private val view: View,
        private val adapter: SimpleRecyclerAdapter
) : SafeView {

    private val recycler: RecyclerView = view.findViewById(R.id.recycler)
    private val createButton: FloatingActionButton = view.findViewById(R.id.create_button)

    private val createRelay = PublishRelay.create<Int>()

    init {
        val orientation = VERTICAL
        val layoutManager = LinearLayoutManager(view.context, orientation, false)
        adapter.setHasStableIds(true)
        recycler.adapter = adapter
        recycler.layoutManager = layoutManager
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.itemAnimator?.changeDuration = DURATION_MEDIUM

        createButton.setOnClickListener { showCreateDialog() }
    }

    override fun showProgress() {

    }

    override fun showContent() {

    }

    override fun contentUpdated() {
        adapter.notifyDataSetChanged()
    }

    override fun createClicks(): Observable<Int> {
        return createRelay
    }

    private fun showCreateDialog() {
        BottomSheetBuilder(view.context, R.style.AppTheme_BottomSheetDialog)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setMenu(R.menu.create_menu)
                .setIconTintColorResource(R.color.color_grey)
                .setItemClickListener {
                    val recordType = when (it.itemId) {
                        R.id.group_item -> TYPE_GROUP
                        R.id.password_item -> TYPE_PASSWORD
                        R.id.card_item -> TYPE_CARD
                        R.id.note_item -> TYPE_NOTE
                        else -> throw IllegalArgumentException()
                    }
                    createRelay.accept(recordType)
                }
                .createDialog()
                .show()
    }

}

private const val DURATION_MEDIUM = 300L
