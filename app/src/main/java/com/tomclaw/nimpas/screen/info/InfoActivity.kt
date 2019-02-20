package com.tomclaw.nimpas.screen.info

import android.content.Context
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
import com.tomclaw.nimpas.screen.info.di.InfoModule
import javax.inject.Inject

class InfoActivity : AppCompatActivity(), InfoPresenter.InfoRouter {

    @Inject
    lateinit var presenter: InfoPresenter

    @Inject
    lateinit var adapterPresenter: AdapterPresenter

    @Inject
    lateinit var binder: ItemBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        val recordId = intent.getRecordId()
        val presenterState = savedInstanceState?.getBundle(KEY_PRESENTER_STATE)
        application.getComponent()
                .infoComponent(InfoModule(recordId, presenterState))
                .inject(activity = this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_activity)

        val adapter = SimpleRecyclerAdapter(adapterPresenter, binder)
        val view = InfoViewImpl(window.decorView, adapter)

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
            REQUEST_EDIT -> {
                if (resultCode == RESULT_OK) {
                    presenter.onUpdate()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun showEditScreen(record: Record) {
        val intent = createFormActivityIntent(context = this, record = record)
        startActivityForResult(intent, REQUEST_EDIT)
    }

    override fun leaveScreen(changed: Boolean) {
        val result = if (changed) RESULT_OK else RESULT_CANCELED
        setResult(result)
        finish()
    }

    private fun Intent.getRecordId() = getLongExtra(EXTRA_RECORD_ID, 0)

}

fun createInfoActivityIntent(
        context: Context,
        recordId: Long
): Intent = Intent(context, InfoActivity::class.java)
        .putExtra(EXTRA_RECORD_ID, recordId)

private const val KEY_PRESENTER_STATE = "presenter_state"

private const val REQUEST_EDIT = 1

private const val EXTRA_RECORD_ID = "record_id"
