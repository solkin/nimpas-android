package com.tomclaw.nimpas.screen.form

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.journal.GROUP_DEFAULT
import com.tomclaw.nimpas.main.getComponent
import com.tomclaw.nimpas.screen.form.di.FormModule
import javax.inject.Inject

class FormActivity : AppCompatActivity(), FormPresenter.FormRouter {

    @Inject
    lateinit var presenter: FormPresenter

    @Inject
    lateinit var adapterPresenter: AdapterPresenter

    @Inject
    lateinit var binder: ItemBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        val recordType = 0//intent.getRecordType()
        val groupId = 0L//intent.getGroupId()
        val presenterState = savedInstanceState?.getBundle(KEY_PRESENTER_STATE)
        application.getComponent()
                .formComponent(FormModule(this, recordType, groupId, presenterState))
                .inject(activity = this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_activity)


        val adapter = SimpleRecyclerAdapter(adapterPresenter, binder)
        val view = FormViewImpl(window.decorView, adapter)

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

    private fun Intent.getRecordType() = this.getIntExtra(EXTRA_RECORD_TYPE, RECORD_TYPE_INVALID).apply {
        if (this == RECORD_TYPE_INVALID) {
            throw IllegalArgumentException("Record type must be specified")
        }
    }

    private fun Intent.getGroupId() = this.getLongExtra(EXTRA_GROUP_ID, GROUP_DEFAULT)

}

fun createFormActivityIntent(context: Context,
                             recordType: Int,
                             groupId: Long): Intent =
        Intent(context, FormActivity::class.java)
                .putExtra(EXTRA_RECORD_TYPE, recordType)
                .putExtra(EXTRA_GROUP_ID, groupId)

private const val KEY_PRESENTER_STATE = "presenter_state"

private const val EXTRA_RECORD_TYPE = "record_type"
private const val EXTRA_GROUP_ID = "group_id"

private const val RECORD_TYPE_INVALID = -1
