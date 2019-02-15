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
        val record = intent.getRecord()
        val presenterState = savedInstanceState?.getBundle(KEY_PRESENTER_STATE)
        application.getComponent()
                .infoComponent(InfoModule(record, presenterState))
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

    override fun leaveScreen() {
        setResult(RESULT_OK)
        finish()
    }

    private fun Intent.getRecord() = this.getParcelableExtra<Record>(EXTRA_RECORD)

}

fun createInfoActivityIntent(
        context: Context,
        record: Record
): Intent = Intent(context, InfoActivity::class.java)
        .putExtra(EXTRA_RECORD, record)

private const val KEY_PRESENTER_STATE = "presenter_state"

private const val EXTRA_RECORD = "record"
