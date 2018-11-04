package com.tomclaw.nimpas.screen.form.adapter.input

import com.avito.konveyor.blueprint.ItemPresenter
import com.tomclaw.nimpas.screen.safe.adapter.ItemClickListener

class InputItemPresenter(private val listener: ItemClickListener) : ItemPresenter<InputItemView, InputItem> {

    override fun bindView(view: InputItemView, item: InputItem, position: Int) {
        view.setIcon(item.id)
        view.setTitle(item.title)
        view.setText(item.text)
        view.setOnClickListener { listener.onItemClick(item) }
    }

}