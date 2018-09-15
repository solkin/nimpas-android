package com.tomclaw.nimpas.screen.safe

import android.view.View
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.avito.konveyor.blueprint.Item
import io.reactivex.Observable

interface SafeView {

    fun showProgress()

    fun showContent()

    fun itemClicks(): Observable<Item>

}

class SafeViewImpl(
        private val view: View,
        private val adapter: SimpleRecyclerAdapter
) : SafeView {

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showContent() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun itemClicks(): Observable<Item> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}