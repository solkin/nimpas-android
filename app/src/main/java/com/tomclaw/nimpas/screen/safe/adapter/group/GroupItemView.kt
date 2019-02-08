package com.tomclaw.nimpas.screen.safe.adapter.group

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.CircleIconView
import com.tomclaw.nimpas.util.bind
import com.tomclaw.nimpas.util.randomColor

interface GroupItemView : ItemView {

    fun setIcon(svg: String, itemId: Long)

    fun setTitle(title: String)

    fun setOnClickListener(listener: (() -> Unit)?)

}

class GroupItemViewHolder(view: View) : BaseViewHolder(view), GroupItemView {

    private val icon: CircleIconView = view.findViewById(R.id.icon)
    private val title: TextView = view.findViewById(R.id.title)

    private var listener: (() -> Unit)? = null

    init {
        view.setOnClickListener { listener?.invoke() }
    }

    override fun setIcon(svg: String, itemId: Long) {
        val pair = randomColor(itemId)
        icon.setIconColoredRes(svg, pair.second, pair.first)
    }

    override fun setTitle(title: String) {
        this.title.bind(title)
    }

    override fun setOnClickListener(listener: (() -> Unit)?) {
        this.listener = listener
    }

    override fun onUnbind() {
        this.listener = null
    }

}