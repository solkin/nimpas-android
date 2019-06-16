package com.tomclaw.nimpas.screen.lock

import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.jakewharton.rxrelay2.PublishRelay
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.changes
import com.tomclaw.nimpas.util.clicks
import io.reactivex.Observable

interface LockView {

    fun keywordChanges(): Observable<String>

    fun unlockClicks(): Observable<Unit>

    fun switchClicks(): Observable<Unit>

    fun showUnlockError()

}

class LockViewImpl(view: View) : LockView {

    private val toolbar: Toolbar = view.findViewById(R.id.toolbar)
    private val formContainer: View = view.findViewById(R.id.form_container)
    private val keywordView: EditText = view.findViewById(R.id.keyword_view)
    private val unlockButton: Button = view.findViewById(R.id.unlock_button)
    private val switchButton: Button = view.findViewById(R.id.switch_button)

    private val keywordChangesRelay = PublishRelay.create<String>()
    private val unlockClicksRelay = PublishRelay.create<Unit>()
    private val switchClicksRelay = PublishRelay.create<Unit>()

    init {
        toolbar.setTitle(R.string.app_name)
        keywordView.changes { keywordChangesRelay.accept(it) }
        unlockButton.clicks(unlockClicksRelay)
        switchButton.clicks(switchClicksRelay)
    }

    override fun keywordChanges(): Observable<String> {
        return keywordChangesRelay
    }

    override fun unlockClicks(): Observable<Unit> {
        return unlockClicksRelay
    }

    override fun switchClicks(): Observable<Unit> {
        return switchClicksRelay
    }

    override fun showUnlockError() {
        Snackbar.make(formContainer, R.string.unlock_failed, Snackbar.LENGTH_SHORT).show()
    }

}
