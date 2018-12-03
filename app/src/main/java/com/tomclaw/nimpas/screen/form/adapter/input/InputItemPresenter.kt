package com.tomclaw.nimpas.screen.form.adapter.input

import com.avito.konveyor.blueprint.ItemPresenter

class InputItemPresenter() : ItemPresenter<InputItemView, InputItem> {

    override fun bindView(view: InputItemView, item: InputItem, position: Int) {
        view.setHint(item.hint)
        view.setText(item.text)
    }

}