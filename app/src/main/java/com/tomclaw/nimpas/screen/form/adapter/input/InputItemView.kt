package com.tomclaw.nimpas.screen.form.adapter.input

import android.view.View
import android.widget.EditText
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.bind

interface InputItemView : ItemView {

    fun setHint(hint: String)

    fun setText(text: String)

    fun setOnClickListener(listener: (() -> Unit)?)

}

class InputItemViewHolder(view: View) : BaseViewHolder(view), InputItemView {

    private val input: EditText = view.findViewById(R.id.input)
    private var listener: (() -> Unit)? = null

    init {
        view.setOnClickListener { listener?.invoke() }
    }

    override fun setHint(hint: String) {
        input.hint = hint
    }

    override fun setText(text: String) {
        input.bind(text)
    }

    override fun setOnClickListener(listener: (() -> Unit)?) {
        this.listener = listener
    }

    override fun onUnbind() {
        this.listener = null
    }

}