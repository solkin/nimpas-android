package com.tomclaw.nimpas.screen.form.adapter.action

import androidx.annotation.ColorInt
import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.CircleIconView
import com.tomclaw.nimpas.util.bind

interface ActionItemView : ItemView {

    fun setTitle(text: String)

    fun setIcon(svg: String, @ColorInt color: Int)

    fun setOnClickListener(listener: (() -> Unit)?)

}

class ActionItemViewHolder(view: View) : BaseViewHolder(view), ActionItemView {

    private val title: TextView = view.findViewById(R.id.title)
    private val icon: CircleIconView = view.findViewById(R.id.icon)

    private var listener: (() -> Unit)? = null

    init {
        view.setOnClickListener { listener?.invoke() }
    }

    override fun setTitle(text: String) {
        title.bind(text)
    }

    override fun setIcon(svg: String, @ColorInt color: Int) {
        icon.setIconColored(svg, background = color)
    }

    override fun setOnClickListener(listener: (() -> Unit)?) {
        this.listener = listener
    }

    override fun onUnbind() {
        this.listener = null
    }

}