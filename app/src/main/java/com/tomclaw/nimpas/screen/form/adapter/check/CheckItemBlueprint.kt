package com.tomclaw.nimpas.screen.form.adapter.check

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import com.tomclaw.nimpas.R

class CheckItemBlueprint(
        override val presenter: ItemPresenter<CheckItemView, CheckItem>
) : ItemBlueprint<CheckItemView, CheckItem> {

    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.check_item,
            creator = { _, view -> CheckItemViewHolder(view) }
    )

    override fun isRelevantItem(item: Item) = item is CheckItem

}