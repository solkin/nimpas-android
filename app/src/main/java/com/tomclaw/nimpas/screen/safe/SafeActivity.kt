package com.tomclaw.nimpas.screen.safe

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.journal.Record
import com.tomclaw.nimpas.main.getComponent
import com.tomclaw.nimpas.screen.form.createFormActivityIntent
import com.tomclaw.nimpas.screen.info.createInfoActivityIntent
import com.tomclaw.nimpas.screen.lock.createLockActivityIntent
import com.tomclaw.nimpas.screen.safe.di.SafeModule
import javax.inject.Inject

class SafeActivity : AppCompatActivity(), SafePresenter.SafeRouter {

    @Inject
    lateinit var presenter: SafePresenter

    @Inject
    lateinit var adapterPresenter: AdapterPresenter

    @Inject
    lateinit var binder: ItemBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        val presenterState = savedInstanceState?.getBundle(KEY_PRESENTER_STATE)
        application.getComponent()
                .safeComponent(SafeModule(this, presenterState))
                .inject(activity = this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.safe_activity)

        val adapter = SimpleRecyclerAdapter(adapterPresenter, binder)
        val view = SafeViewImpl(window.decorView, adapter)

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_ADD,
            REQUEST_UNLOCK -> {
                if (resultCode == RESULT_OK) {
                    presenter.onUpdate()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun showFormScreen(templateId: Long, groupId: Long) {
        val intent = createFormActivityIntent(
                context = this,
                templateId = templateId,
                groupId = groupId
        )
        startActivityForResult(intent, REQUEST_ADD)
    }

    override fun showLockScreen() {
        val intent = createLockActivityIntent(context = this)
        startActivityForResult(intent, REQUEST_UNLOCK)
    }

    override fun showInfo(record: Record) {
        val intent = createInfoActivityIntent(context = this, recordId = record.id)
        startActivity(intent)
    }

    override fun leaveScreen() {
        finish()
    }

}

private const val KEY_PRESENTER_STATE = "presenter_state"

private const val REQUEST_ADD = 1
private const val REQUEST_UNLOCK = 2
