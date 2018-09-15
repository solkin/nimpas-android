package com.tomclaw.nimpas.screen.safe.adapter.view

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R

interface CardItemView : ItemView {

    fun setTitle(title: String)

}

class CardItemViewHolder(view: View) : BaseViewHolder(view), CardItemView {

    private val title: TextView = view.findViewById(R.id.title)

    override fun setTitle(title: String) {
        this.title.text = title
    }

}