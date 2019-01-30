package com.tomclaw.nimpas.screen.form.adapter.button

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R

interface ButtonItemView : ItemView {

    fun setTitle(text: String)

    fun setOnClickListener(listener: (() -> Unit)?)

}

class ButtonItemViewHolder(view: View) : BaseViewHolder(view), ButtonItemView {

    private val context = view.context

    private val button: TextView = view.findViewById(R.id.button)

    private var listener: (() -> Unit)? = null

    init {
        view.setOnClickListener { listener?.invoke() }
    }

    override fun setTitle(text: String) {
        button.text = text
    }

    override fun setOnClickListener(listener: (() -> Unit)?) {
        this.listener = listener
    }

    override fun onUnbind() {
        this.listener = null
    }

}