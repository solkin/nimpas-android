package com.tomclaw.nimpas.screen.safe

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.main.getComponent
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
            REQUEST_ADD -> {
                if (resultCode == RESULT_OK) {
                    presenter.onUpdate()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun leaveScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

private const val KEY_PRESENTER_STATE = "presenter_state"
private const val REQUEST_ADD = 1
