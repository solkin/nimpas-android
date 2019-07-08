package com.tomclaw.nimpas.screen.safe.adapter.pass

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.CircleIconView
import com.tomclaw.nimpas.util.bind
import com.tomclaw.nimpas.util.randomColor

interface PasswordItemView : ItemView {

    fun setIcon(svg: String, itemId: Long)

    fun setTitle(title: String)

    fun setSubtitle(subtitle: String?)

    fun setOnClickListener(listener: (() -> Unit)?)

}

class PasswordItemViewHolder(view: View) : BaseViewHolder(view), PasswordItemView {

    private val icon: CircleIconView = view.findViewById(R.id.icon)
    private val title: TextView = view.findViewById(R.id.title)
    private val subtitle: TextView = view.findViewById(R.id.subtitle)

    private var listener: (() -> Unit)? = null

    init {
        view.setOnClickListener { listener?.invoke() }
    }

    override fun setIcon(svg: String, itemId: Long) {
        val pair = randomColor(itemId)
        icon.setIconColoredRes(svg, color = pair.second, background = pair.first)
    }

    override fun setTitle(title: String) {
        this.title.bind(title)
    }

    override fun setSubtitle(subtitle: String?) {
        this.subtitle.bind(subtitle)
    }

    override fun setOnClickListener(listener: (() -> Unit)?) {
        this.listener = listener
    }

    override fun onUnbind() {
        this.listener = null
    }

}