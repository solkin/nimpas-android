package com.tomclaw.nimpas.screen.safe

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.MATCH_DEFAULT_ONLY
import android.os.Bundle
import android.support.v4.app.ShareCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.main.getComponent
import com.tomclaw.nimpas.screen.form.createFormActivityIntent
import com.tomclaw.nimpas.screen.info.createInfoActivityIntent
import com.tomclaw.nimpas.screen.lock.createLockActivityIntent
import com.tomclaw.nimpas.screen.safe.di.SafeModule
import com.tomclaw.nimpas.storage.Record
import com.tomclaw.nimpas.util.getUndo
import java.io.File
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
            REQUEST_UNLOCK,
            REQUEST_INFO -> {
                if (resultCode == RESULT_OK) {
                    presenter.onUpdate()
                }
            }
        }
        data?.getUndo()?.let { presenter.onShowUndo(it) }
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
        startActivityForResult(intent, REQUEST_INFO)
    }

    override fun showExportScreen(file: File) {
        val uri = FileProvider.getUriForFile(this, packageName, file)
        ShareCompat.IntentBuilder.from(this)
                .setStream(uri)
                .setType(MIME_TYPE)
                .intent
                .setAction(Intent.ACTION_SEND)
                .setDataAndType(uri, MIME_TYPE)
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                .run {
                    val list = packageManager.queryIntentActivities(this, MATCH_DEFAULT_ONLY)
                    for (resolveInfo in list) {
                        val packageName = resolveInfo.activityInfo.packageName
                        grantUriPermission(packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    if (resolveActivity(packageManager) != null) {
                        startActivity(this)
                    }
                }
    }

    override fun leaveScreen() {
        finish()
    }

}

fun createSafeActivityIntent(context: Context): Intent =
        Intent(context, SafeActivity::class.java)

private const val KEY_PRESENTER_STATE = "presenter_state"

private const val REQUEST_ADD = 1
private const val REQUEST_UNLOCK = 2
private const val REQUEST_INFO = 3

private const val MIME_TYPE = "application/nimpas-safe"
