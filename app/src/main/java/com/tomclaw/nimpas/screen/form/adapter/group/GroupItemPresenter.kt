package com.tomclaw.nimpas.screen.form.adapter.group

import com.avito.konveyor.blueprint.ItemPresenter

class GroupItemPresenter : ItemPresenter<GroupItemView, GroupItem> {

    override fun bindView(view: GroupItemView, item: GroupItem, position: Int) {
        view.setTitle(item.title)
    }

}