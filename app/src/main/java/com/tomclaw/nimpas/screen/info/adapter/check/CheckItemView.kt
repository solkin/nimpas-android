package com.tomclaw.nimpas.screen.info.adapter.check

import android.view.View
import android.widget.CheckBox
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R

interface CheckItemView : ItemView {

    fun setText(text: String)

    fun setChecked(checked: Boolean)

}

class CheckItemViewHolder(view: View) : BaseViewHolder(view), CheckItemView {

    private val check: CheckBox = view.findViewById(R.id.check)

    init {
        check.isEnabled = false
    }

    override fun setText(text: String) {
        check.text = text
    }

    override fun setChecked(checked: Boolean) {
        check.isChecked = checked
    }

}