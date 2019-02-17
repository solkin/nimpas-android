package com.tomclaw.nimpas.screen.info.adapter.text

import android.view.View
import android.widget.TextView
import android.widget.ViewSwitcher
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R

interface TextItemView : ItemView {

    fun setHint(value: String)

    fun setText(value: String?)

}

class TextItemViewHolder(view: View) : BaseViewHolder(view), TextItemView {

    private val switcher: ViewSwitcher = view as ViewSwitcher
    private val hint: TextView = view.findViewById(R.id.hint)
    private val head: TextView = view.findViewById(R.id.head)
    private val text: TextView = view.findViewById(R.id.text)

    override fun setHint(value: String) {
        hint.text = value
        head.text = value
    }

    override fun setText(value: String?) {
        if (value.isNullOrEmpty()) {
            hideText()
        } else {
            text.text = value
            showText()
        }
    }

    private fun hideText() {
        switcher.displayedChild = 0
    }

    private fun showText() {
        switcher.displayedChild = 1
    }

}