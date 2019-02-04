package com.tomclaw.nimpas.screen.form.adapter.edit

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R

interface EditItemView : ItemView {

    fun setHint(hint: String)

    fun setText(text: String)

    fun setTextChangedListener(listener: ((String) -> Unit)?)

}

class EditItemViewHolder(view: View) : BaseViewHolder(view), EditItemView {

    private val edit: EditText = view.findViewById(R.id.edit)
    private var listener: ((String) -> Unit)? = null

    init {
        edit.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener?.invoke(s.toString())
            }

        })
    }

    override fun setHint(hint: String) {
        edit.hint = hint
    }

    override fun setText(text: String) {
        edit.setText(text)
    }

    override fun setTextChangedListener(listener: ((String) -> Unit)?) {
        this.listener = listener
    }

    override fun onUnbind() {
        this.listener = null
    }

}