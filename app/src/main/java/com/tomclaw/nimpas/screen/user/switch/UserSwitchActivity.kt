package com.tomclaw.nimpas.screen.user.switch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.main.getComponent
import com.tomclaw.nimpas.screen.user.switch.di.UserSwitchModule
import javax.inject.Inject

class UserSwitchActivity : AppCompatActivity(), UserSwitchPresenter.UserSwitchRouter {

    @Inject
    lateinit var presenter: UserSwitchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        val presenterState = savedInstanceState?.getBundle(KEY_PRESENTER_STATE)
        application.getComponent()
                .userSwitchComponent(UserSwitchModule(this, presenterState))
                .inject(activity = this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_switch_activity)

        val view = UserSwitchViewImpl(window.decorView)

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
        finish()
    }

}

fun createSwitchUserActivityIntent(context: Context): Intent =
        Intent(context, UserSwitchActivity::class.java)

private const val KEY_PRESENTER_STATE = "presenter_state"
