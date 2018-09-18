package com.tomclaw.nimpas.screen.safe.adapter.group

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.bind

interface GroupItemView : ItemView {

    fun setTitle(title: String)

}

class GroupItemViewHolder(view: View) : BaseViewHolder(view), GroupItemView {

    private val title: TextView = view.findViewById(R.id.title)

    override fun setTitle(title: String) {
        this.title.bind(title)
    }

}