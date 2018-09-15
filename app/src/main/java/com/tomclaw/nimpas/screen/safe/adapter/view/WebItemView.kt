package com.tomclaw.nimpas.screen.safe.adapter.view

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R

interface WebItemView : ItemView {

    fun setTitle(title: String)

}

class WebItemViewHolder(view: View) : BaseViewHolder(view), WebItemView {

    private val title: TextView = view.findViewById(R.id.title)

    override fun setTitle(title: String) {
        this.title.text = title
    }

}