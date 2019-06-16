package com.tomclaw.nimpas.screen.book.add

import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.jakewharton.rxrelay2.PublishRelay
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.changes
import com.tomclaw.nimpas.util.clicks

interface BookAddView {

}

class BookAddViewImpl(view: View) : BookAddView {

    private val toolbar: Toolbar = view.findViewById(R.id.toolbar)
    private val titleView: EditText = view.findViewById(R.id.book_title_view)
    private val keywordView: EditText = view.findViewById(R.id.keyword_view)
    private val bookAddButton: Button = view.findViewById(R.id.book_add_button)

    private val titleChangesRelay = PublishRelay.create<String>()
    private val keywordChangesRelay = PublishRelay.create<String>()
    private val bookAddClicksRelay = PublishRelay.create<Unit>()

    init {
        toolbar.setTitle(R.string.add_book)
        titleView.changes { titleChangesRelay.accept(it) }
        keywordView.changes { keywordChangesRelay.accept(it) }
        bookAddButton.clicks(bookAddClicksRelay)
    }

}
