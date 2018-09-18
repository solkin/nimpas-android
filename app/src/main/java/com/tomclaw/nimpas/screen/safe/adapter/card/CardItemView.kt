package com.tomclaw.nimpas.screen.safe.adapter.card

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.bind

interface CardItemView : ItemView {

    fun setImage(image: Int)

    fun setTitle(title: String)

    fun setNumber(number: String)

}

class CardItemViewHolder(view: View) : BaseViewHolder(view), CardItemView {

    private val title: TextView = view.findViewById(R.id.title)
    private val number: TextView = view.findViewById(R.id.number)

    override fun setImage(image: Int) {

    }

    override fun setTitle(title: String) {
        this.title.bind(title)
    }

    override fun setNumber(number: String) {
        this.number.bind(number)
    }

}