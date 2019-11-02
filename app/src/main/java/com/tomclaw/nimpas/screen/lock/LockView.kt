package com.tomclaw.nimpas.screen.lock

import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.jakewharton.rxrelay2.PublishRelay
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.changes
import com.tomclaw.nimpas.util.clicks
import io.reactivex.Observable

interface LockView {

    fun setTitle(title: String)

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

    override fun setTitle(title: String) {
        toolbar.title = title
    }

    override fun keywordChanges() = keywordChangesRelay

    override fun unlockClicks() = unlockClicksRelay

    override fun switchClicks() = switchClicksRelay

    override fun showUnlockError() {
        Snackbar.make(formContainer, R.string.unlock_failed, Snackbar.LENGTH_SHORT).show()
    }

}
