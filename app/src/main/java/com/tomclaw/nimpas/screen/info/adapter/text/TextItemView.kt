package com.tomclaw.nimpas.screen.info.adapter.text

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.bind

interface TextItemView : ItemView {

    fun setTitle(text: String)

}

class TextItemViewHolder(view: View) : BaseViewHolder(view), TextItemView {

    private val title: TextView = view.findViewById(R.id.text)

    override fun setTitle(text: String) {
        title.bind(text)
    }

}