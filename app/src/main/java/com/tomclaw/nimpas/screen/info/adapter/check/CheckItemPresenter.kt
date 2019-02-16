package com.tomclaw.nimpas.screen.info.adapter.check

import com.avito.konveyor.blueprint.ItemPresenter

class CheckItemPresenter : ItemPresenter<CheckItemView, CheckItem> {

    override fun bindView(view: CheckItemView, item: CheckItem, position: Int) {
        view.setText(item.text)
        view.setChecked(item.checked)
    }

}