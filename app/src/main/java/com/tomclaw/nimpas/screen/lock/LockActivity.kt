package com.tomclaw.nimpas.screen.lock

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.main.getComponent
import com.tomclaw.nimpas.screen.book.list.createBookListActivityIntent
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBundle(KEY_PRESENTER_STATE, presenter.saveState())
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        presenter.updateBookTitle()
    }

    override fun showBookListScreen() {
        val intent = createBookListActivityIntent(this)
        startActivity(intent)
    }

    override fun leaveScreen(isUnlocked: Boolean) {
        val result = if (isUnlocked) RESULT_OK else RESULT_CANCELED
        setResult(result)
        intent.getTargetIntent()?.let { target ->
            if (isUnlocked) startActivity(target)
        }
        finish()
    }

    private fun Intent.getTargetIntent() = getParcelableExtra<Intent>(EXTRA_TARGET_INTENT)

}

fun createLockActivityIntent(context: Context, target: Intent? = null): Intent =
        Intent(context, LockActivity::class.java)
                .putExtra(EXTRA_TARGET_INTENT, target)
                .setFlags(FLAG_ACTIVITY_CLEAR_TOP or FLAG_ACTIVITY_SINGLE_TOP)

private const val KEY_PRESENTER_STATE = "presenter_state"

private const val EXTRA_TARGET_INTENT = "target_intent"
