package com.tomclaw.nimpas.screen.lock

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.main.getComponent
import com.tomclaw.nimpas.screen.lock.di.LockModule
import javax.inject.Inject

class LockActivity : AppCompatActivity(), LockPresenter.LockRouter {

    @Inject
    lateinit var presenter: LockPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        val presenterState = savedInstanceState?.getBundle(KEY_PRESENTER_STATE)
        application.getComponent()
                .lockComponent(LockModule(this, presenterState))
                .inject(activity = this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.lock_activity)

        val view = LockViewImpl(window.decorView)

        presenter.attachView(view)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachRouter(this)
    }

    override fun onStop() {
        presenter.detachRouter()
        super.onStop()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBundle(KEY_PRESENTER_STATE, presenter.saveState())
    }

    override fun leaveScreen(isUnlocked: Boolean) {
        val result = if (isUnlocked) { RESULT_OK } else { RESULT_CANCELED }
        setResult(result)
        finish()
    }

}

fun createLockActivityIntent(context: Context): Intent =
        Intent(context, LockActivity::class.java)

private const val KEY_PRESENTER_STATE = "presenter_state"
