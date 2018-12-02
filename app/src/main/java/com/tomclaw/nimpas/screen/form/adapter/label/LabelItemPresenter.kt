package com.tomclaw.nimpas.screen.form.adapter.label

import com.avito.konveyor.blueprint.ItemPresenter
import com.tomclaw.nimpas.screen.form.adapter.input.InputItem
import com.tomclaw.nimpas.screen.safe.adapter.ItemClickListener

class LabelItemPresenter() : ItemPresenter<LabelItemView, LabelItem> {

    override fun bindView(view: LabelItemView, item: LabelItem, position: Int) {
        view.setText(item.text)
    }

}