package com.tomclaw.nimpas.screen.lock

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.jakewharton.rxrelay2.PublishRelay
import com.tomclaw.nimpas.R
import io.reactivex.Observable

interface LockView {

    fun showProgress()

    fun showContent()

    fun keywordChanges(): Observable<String>

    fun unlockClicks(): Observable<Unit>

}

class LockViewImpl(
        private val view: View
) : LockView {

    private val keywordView: EditText = view.findViewById(R.id.keyword_view)
    private val unlockButton: Button = view.findViewById(R.id.unlock_button)

    private val keywordChangesRelay = PublishRelay.create<String>()
    private val unlockClicksRelay = PublishRelay.create<Unit>()

    init {
        keywordView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable) {}

            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                keywordChangesRelay.accept(text.toString())
            }
        })
        unlockButton.setOnClickListener { unlockClicksRelay.accept(Unit) }
    }

    override fun showProgress() {

    }

    override fun showContent() {

    }
    override fun keywordChanges(): Observable<String> {
        return keywordChangesRelay
    }

    override fun unlockClicks(): Observable<Unit> {
        return unlockClicksRelay
    }

}
