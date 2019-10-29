package com.tomclaw.nimpas.screen.book.importing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.main.getComponent
import com.tomclaw.nimpas.screen.book.importing.di.BookImportModule
import com.tomclaw.nimpas.screen.lock.createLockActivityIntent
import com.tomclaw.nimpas.screen.safe.createSafeActivityIntent
import javax.inject.Inject

class BookImportActivity : AppCompatActivity(), BookImportPresenter.BookImportRouter {

    @Inject
    lateinit var presenter: BookImportPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        val presenterState = savedInstanceState?.getBundle(KEY_PRESENTER_STATE)
        application.getComponent()
                .bookImportComponent(BookImportModule(this, presenterState))
                .inject(activity = this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_import_activity)

        val view = BookImportViewImpl(window.decorView)

        presenter.attachView(view)

        intent.data?.let {
            intent.data = null
            presenter.onImportBook(it)
        } ?: finish()
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

fun createBookImportActivityIntent(context: Context): Intent =
        Intent(context, BookImportActivity::class.java)

private const val KEY_PRESENTER_STATE = "presenter_state"
