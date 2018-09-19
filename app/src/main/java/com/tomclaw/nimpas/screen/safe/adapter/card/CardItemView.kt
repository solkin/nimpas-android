package com.tomclaw.nimpas.screen.safe.adapter.card

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.ItemImageView
import com.tomclaw.nimpas.util.bind

interface CardItemView : ItemView {

    fun setIcon(itemId: Long)

    fun setTitle(title: String)

    fun setNumber(number: String)

    fun setOnClickListener(listener: (() -> Unit)?)

}

class CardItemViewHolder(view: View) : BaseViewHolder(view), CardItemView {

    private val icon: ItemImageView = view.findViewById(R.id.icon)
    private val title: TextView = view.findViewById(R.id.title)
    private val number: TextView = view.findViewById(R.id.number)
    private var listener: (() -> Unit)? = null

    init {
        view.setOnClickListener { listener?.invoke() }
    }

    override fun setIcon(itemId: Long) {
        icon.setItemId(itemId)
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