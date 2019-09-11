package com.tomclaw.nimpas.screen.book.importing

import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.jakewharton.rxrelay2.PublishRelay
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.changes
import com.tomclaw.nimpas.util.clicks
import io.reactivex.Observable

interface BookImportView {

    fun navigationClicks(): Observable<Unit>

    fun titleChanges(): Observable<String>

    fun keywordChanges(): Observable<String>

    fun bookAddClicks(): Observable<Unit>

}

class BookImportViewImpl(view: View) : BookImportView {

    private val toolbar: Toolbar = view.findViewById(R.id.toolbar)
    private val titleView: EditText = view.findViewById(R.id.book_title_view)
    private val keywordView: EditText = view.findViewById(R.id.keyword_view)
    private val bookAddButton: Button = view.findViewById(R.id.book_add_button)

    private val navigationRelay = PublishRelay.create<Unit>()
    private val titleChangesRelay = PublishRelay.create<String>()
    private val keywordChangesRelay = PublishRelay.create<String>()
    private val bookAddClicksRelay = PublishRelay.create<Unit>()

    init {
        toolbar.setTitle(R.string.add_book)
        toolbar.setNavigationOnClickListener {
            navigationRelay.accept(Unit)
        }
        titleView.changes { titleChangesRelay.accept(it) }
        keywordView.changes { keywordChangesRelay.accept(it) }
        bookAddButton.clicks(bookAddClicksRelay)
    }

    override fun navigationClicks(): Observable<Unit> = navigationRelay

    override fun titleChanges() = titleChangesRelay

    override fun keywordChanges() = keywordChangesRelay

    override fun bookAddClicks() = bookAddClicksRelay

}
