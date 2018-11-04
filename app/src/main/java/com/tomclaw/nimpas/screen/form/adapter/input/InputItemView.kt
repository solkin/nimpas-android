package com.tomclaw.nimpas.screen.form.adapter.input

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.ItemImageView
import com.tomclaw.nimpas.util.bind

interface InputItemView : ItemView {

    fun setIcon(itemId: Long)

    fun setTitle(title: String)

    fun setText(text: String)

    fun setOnClickListener(listener: (() -> Unit)?)

}

class InputItemViewHolder(view: View) : BaseViewHolder(view), InputItemView {

    private val icon: ItemImageView = view.findViewById(R.id.icon)
    private val title: TextView = view.findViewById(R.id.title)
    private val text: TextView = view.findViewById(R.id.text)
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

    override fun setText(text: String) {
        this.text.bind(text)
    }

    override fun setOnClickListener(listener: (() -> Unit)?) {
        this.listener = listener
    }

    override fun onUnbind() {
        this.listener = null
    }

}