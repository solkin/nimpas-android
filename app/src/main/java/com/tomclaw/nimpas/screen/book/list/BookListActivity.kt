package com.tomclaw.nimpas.screen.book.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.main.getComponent
import com.tomclaw.nimpas.screen.book.add.createBookAddActivityIntent
import com.tomclaw.nimpas.screen.book.list.di.BookListModule
import com.tomclaw.nimpas.screen.lock.createLockActivityIntent
import com.tomclaw.nimpas.screen.safe.createSafeActivityIntent
import javax.inject.Inject

class BookListActivity : AppCompatActivity(), BookListPresenter.BookListRouter {

    @Inject
    lateinit var presenter: BookListPresenter

    @Inject
    lateinit var adapterPresenter: AdapterPresenter

    @Inject
    lateinit var binder: ItemBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        val presenterState = savedInstanceState?.getBundle(KEY_PRESENTER_STATE)
        application.getComponent()
                .bookListComponent(BookListModule(this, presenterState))
                .inject(activity = this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_list_activity)

        val adapter = SimpleRecyclerAdapter(adapterPresenter, binder)
        val view = BookListViewImpl(window.decorView, adapter)

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

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_ADD -> {
                if (resultCode == RESULT_OK) {
                    presenter.onUpdate()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun showLockScreen() {
        val target = createSafeActivityIntent(context = this)
        val intent = createLockActivityIntent(context = this, target = target)
        startActivity(intent)
        leaveScreen()
    }

    override fun showBookAddScreen() {
        val intent = createBookAddActivityIntent(context = this)
        startActivityForResult(intent, REQUEST_ADD)
    }

    override fun leaveScreen() {
        finish()
    }

}

fun createBookListActivityIntent(context: Context): Intent =
        Intent(context, BookListActivity::class.java)

private const val KEY_PRESENTER_STATE = "presenter_state"

private const val REQUEST_ADD = 1
