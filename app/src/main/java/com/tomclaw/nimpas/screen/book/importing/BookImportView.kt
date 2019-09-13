package com.tomclaw.nimpas.screen.book.importing

import android.support.v7.widget.Toolbar
import android.view.View
import com.jakewharton.rxrelay2.PublishRelay
import com.tomclaw.nimpas.R
import io.reactivex.Observable

interface BookImportView {

    fun navigationClicks(): Observable<Unit>

}

class BookImportViewImpl(view: View) : BookImportView {

    private val toolbar: Toolbar = view.findViewById(R.id.toolbar)

    private val navigationRelay = PublishRelay.create<Unit>()

    init {
        toolbar.setTitle(R.string.import_book)
        toolbar.setNavigationOnClickListener {
            navigationRelay.accept(Unit)
        }
    }

    override fun navigationClicks(): Observable<Unit> = navigationRelay

}
