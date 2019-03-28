package com.tomclaw.nimpas.screen.user.list.adapter.user

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.CircleIconView
import com.tomclaw.nimpas.util.bind

interface UserItemView : ItemView {

    fun setTitle(title: String)

    fun setSubtitle(subtitle: String?)

    fun setOnClickListener(listener: (() -> Unit)?)

}

class UserItemViewHolder(view: View) : BaseViewHolder(view), UserItemView {

    private val icon: CircleIconView = view.findViewById(R.id.icon)
    private val title: TextView = view.findViewById(R.id.title)
    private val subtitle: TextView = view.findViewById(R.id.subtitle)

    private var listener: (() -> Unit)? = null

    init {
        view.setOnClickListener { listener?.invoke() }
    }

    override fun setTitle(title: String) {
        this.title.bind(title)
    }

    override fun setSubtitle(subtitle: String?) {
        this.subtitle.bind(subtitle)
    }

    override fun setOnClickListener(listener: (() -> Unit)?) {
        this.listener = listener
    }

    override fun onUnbind() {
        this.listener = null
    }

}