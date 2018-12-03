package com.tomclaw.nimpas.screen.form.adapter.label

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.bind

interface LabelItemView : ItemView {

    fun setText(text: String)

}

class LabelItemViewHolder(view: View) : BaseViewHolder(view), LabelItemView {

    private val label: TextView = view.findViewById(R.id.label)

    override fun setText(text: String) {
        label.bind(text)
    }

}