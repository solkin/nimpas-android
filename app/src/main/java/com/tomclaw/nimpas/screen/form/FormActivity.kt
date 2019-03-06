package com.tomclaw.nimpas.screen.form

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.storage.GROUP_DEFAULT
import com.tomclaw.nimpas.storage.Record
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
        val templateId = intent.getTemplateId()
        val groupId = intent.getGroupId()
        val record = intent.getRecord()
        val presenterState = savedInstanceState?.getBundle(KEY_PRESENTER_STATE)
        application.getComponent()
                .formComponent(FormModule(templateId, groupId, record, presenterState))
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

    override fun leaveScreen(changed: Boolean) {
        val result = if (changed) RESULT_OK else RESULT_CANCELED
        setResult(result)
        finish()
    }

    private fun Intent.getTemplateId(): Long = getLongExtra(EXTRA_TEMPLATE_ID, ID_ROOT)

    private fun Intent.getGroupId(): Long = getLongExtra(EXTRA_GROUP_ID, GROUP_DEFAULT)

    private fun Intent.getRecord(): Record? = getParcelableExtra(EXTRA_RECORD)

}

fun createFormActivityIntent(
        context: Context,
        templateId: Long = ID_ROOT,
        groupId: Long
): Intent = Intent(context, FormActivity::class.java)
        .putExtra(EXTRA_TEMPLATE_ID, templateId)
        .putExtra(EXTRA_GROUP_ID, groupId)

fun createFormActivityIntent(
        context: Context,
        record: Record
): Intent = Intent(context, FormActivity::class.java)
        .putExtra(EXTRA_TEMPLATE_ID, record.template.id)
        .putExtra(EXTRA_GROUP_ID, record.groupId)
        .putExtra(EXTRA_RECORD, record)

private const val KEY_PRESENTER_STATE = "presenter_state"

private const val EXTRA_TEMPLATE_ID = "record_type"
private const val EXTRA_GROUP_ID = "group_id"
private const val EXTRA_RECORD = "record"
