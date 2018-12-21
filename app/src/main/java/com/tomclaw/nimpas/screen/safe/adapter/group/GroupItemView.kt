package com.tomclaw.nimpas.screen.safe.adapter.group

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.ItemImageView
import com.tomclaw.nimpas.util.bind

interface GroupItemView : ItemView {

    fun setIcon(itemId: Long)

    fun setTitle(title: String)

    fun setOnClickListener(listener: (() -> Unit)?)

}

class GroupItemViewHolder(view: View) : BaseViewHolder(view), GroupItemView {

    private val icon: ItemImageView = view.findViewById(R.id.icon)
    private val title: TextView = view.findViewById(R.id.title)

    private var listener: (() -> Unit)? = null

    init {
        view.setOnClickListener { listener?.invoke() }
    }

    override fun setIcon(itemId: Long) {
        this.icon.setItemId(itemId)
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