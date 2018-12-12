package com.tomclaw.nimpas.screen.lock

import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.jakewharton.rxrelay2.PublishRelay
import com.tomclaw.nimpas.R
import io.reactivex.Observable

interface LockView {

    fun keywordChanges(): Observable<String>

    fun unlockClicks(): Observable<Unit>

}

class LockViewImpl(view: View) : LockView {

    private val toolbar: Toolbar = view.findViewById(R.id.toolbar)
    private val keywordView: EditText = view.findViewById(R.id.keyword_view)
    private val unlockButton: Button = view.findViewById(R.id.unlock_button)

    private val keywordChangesRelay = PublishRelay.create<String>()
    private val unlockClicksRelay = PublishRelay.create<Unit>()

    init {
        toolbar.setTitle(R.string.app_name)
        keywordView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                keywordChangesRelay.accept(s.toString())
            }
        })
        unlockButton.setOnClickListener { unlockClicksRelay.accept(Unit) }
    }

    override fun keywordChanges(): Observable<String> {
        return keywordChangesRelay
    }

    override fun unlockClicks(): Observable<Unit> {
        return unlockClicksRelay
    }

}
