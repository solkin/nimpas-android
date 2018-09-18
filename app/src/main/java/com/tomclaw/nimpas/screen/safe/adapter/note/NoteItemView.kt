package com.tomclaw.nimpas.screen.safe.adapter.note

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.ItemImageView
import com.tomclaw.nimpas.util.bind

interface NoteItemView : ItemView {

    fun setIcon(itemId: Long)

    fun setTitle(title: String)

    fun setText(text: String)

}

class NoteItemViewHolder(view: View) : BaseViewHolder(view), NoteItemView {

    private val icon: ItemImageView = view.findViewById(R.id.icon)
    private val title: TextView = view.findViewById(R.id.title)
    private val text: TextView = view.findViewById(R.id.text)

    override fun setIcon(itemId: Long) {
        icon.setItemId(itemId)
    }

    override fun setTitle(title: String) {
        this.title.bind(title)
    }

    override fun setText(text: String) {
        this.text.bind(text)
    }

}