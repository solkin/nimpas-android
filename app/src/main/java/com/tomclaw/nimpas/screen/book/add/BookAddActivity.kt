package com.tomclaw.nimpas.screen.book.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.main.getComponent
import com.tomclaw.nimpas.screen.book.add.di.BookAddModule
import com.tomclaw.nimpas.screen.lock.createLockActivityIntent
import com.tomclaw.nimpas.screen.safe.createSafeActivityIntent
import javax.inject.Inject

class BookAddActivity : AppCompatActivity(), BookAddPresenter.BookAddRouter {

    @Inject
    lateinit var presenter: BookAddPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        val presenterState = savedInstanceState?.getBundle(KEY_PRESENTER_STATE)
        application.getComponent()
                .bookAddComponent(BookAddModule(this, presenterState))
                .inject(activity = this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_add_activity)

        val view = BookAddViewImpl(window.decorView)

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

    override fun showLockScreen() {
        val target = createSafeActivityIntent(context = this)
        val intent = createLockActivityIntent(context = this, target = target)
        startActivity(intent)
        leaveScreen()
    }

    override fun leaveScreen() {
        finish()
    }

}

fun createBookAddActivityIntent(context: Context): Intent =
        Intent(context, BookAddActivity::class.java)

private const val KEY_PRESENTER_STATE = "presenter_state"
