package com.tomclaw.nimpas.screen.form.adapter.edit

import android.view.View
import android.widget.EditText
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R

interface EditItemView : ItemView {

    fun setHint(hint: String)

    fun setText(text: String)

    fun setOnClickListener(listener: (() -> Unit)?)

}

class EditItemViewHolder(view: View) : BaseViewHolder(view), EditItemView {

    private val edit: EditText = view.findViewById(R.id.edit)
    private var listener: (() -> Unit)? = null

    init {
        view.setOnClickListener { listener?.invoke() }
    }

    override fun setHint(hint: String) {
        edit.hint = hint
    }

    override fun setText(text: String) {
        edit.setText(text)
    }

    override fun setOnClickListener(listener: (() -> Unit)?) {
        this.listener = listener
    }

    override fun onUnbind() {
        this.listener = null
    }

}