package com.tomclaw.nimpas.screen.safe.adapter.card

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.CircleIconView
import com.tomclaw.nimpas.util.bind
import com.tomclaw.nimpas.util.randomColor

interface CardItemView : ItemView {

    fun setIcon(svg: String, itemId: Long)

    fun setTitle(title: String)

    fun setNumber(number: String)

    fun setOnClickListener(listener: (() -> Unit)?)

}

class CardItemViewHolder(view: View) : BaseViewHolder(view), CardItemView {

    private val icon: CircleIconView = view.findViewById(R.id.icon)
    private val title: TextView = view.findViewById(R.id.title)
    private val number: TextView = view.findViewById(R.id.number)

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

    override fun setNumber(number: String) {
        this.number.bind(number)
    }

    override fun setOnClickListener(listener: (() -> Unit)?) {
        this.listener = listener
    }

    override fun onUnbind() {
        this.listener = null
    }

}