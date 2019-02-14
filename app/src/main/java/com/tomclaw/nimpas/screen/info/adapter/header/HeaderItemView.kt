package com.tomclaw.nimpas.screen.info.adapter.header

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.bind

interface HeaderItemView : ItemView {

    fun setTitle(text: String)

}

class HeaderItemViewHolder(view: View) : BaseViewHolder(view), HeaderItemView {

    private val title: TextView = view.findViewById(R.id.title)

    override fun setTitle(text: String) {
        title.bind(text)
    }

}