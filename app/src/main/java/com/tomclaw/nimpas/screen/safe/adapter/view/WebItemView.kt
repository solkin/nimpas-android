package com.tomclaw.nimpas.screen.safe.adapter.view

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.bind

interface WebItemView : ItemView {

    fun setImage(image: Int)

    fun setTitle(title: String)

    fun setSubtitle(subtitle: String?)

}

class WebItemViewHolder(view: View) : BaseViewHolder(view), WebItemView {

    private val title: TextView = view.findViewById(R.id.title)
    private val subtitle: TextView = view.findViewById(R.id.subtitle)

    override fun setImage(image: Int) {

    }

    override fun setTitle(title: String) {
        this.title.bind(title)
    }

    override fun setSubtitle(subtitle: String?) {
        this.subtitle.bind(subtitle)
    }

}