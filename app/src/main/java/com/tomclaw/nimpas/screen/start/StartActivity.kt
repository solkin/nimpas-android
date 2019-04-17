package com.tomclaw.nimpas.screen.start

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.main.getComponent
import com.tomclaw.nimpas.screen.lock.createLockActivityIntent
import com.tomclaw.nimpas.screen.safe.createSafeActivityIntent
import com.tomclaw.nimpas.screen.start.di.StartModule
import com.tomclaw.nimpas.screen.book.list.createBookListActivityIntent
import javax.inject.Inject

class StartActivity : AppCompatActivity(), StartPresenter.StartRouter {

    @Inject
    lateinit var presenter: StartPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        val presenterState = savedInstanceState?.getBundle(KEY_PRESENTER_STATE)
        application.getComponent()
                .startComponent(StartModule(this, presenterState))
                .inject(activity = this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        val view = StartViewImpl(window.decorView)

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

    override fun showLockScreen() {
        val target = createSafeActivityIntent(context = this)
        val intent = createLockActivityIntent(context = this, target = target)
        startActivity(intent)
        leaveScreen()
    }

    override fun showBookListScreen() {
        val intent = createBookListActivityIntent(context = this)
        startActivity(intent)
        leaveScreen()
    }

    override fun leaveScreen() {
        finish()
    }

}

fun createStartActivityIntent(context: Context): Intent =
        Intent(context, StartActivity::class.java)

private const val KEY_PRESENTER_STATE = "presenter_state"
