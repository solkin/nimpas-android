package com.tomclaw.nimpas.screen.safe.adapter.pass

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import com.tomclaw.nimpas.R

class PasswordItemBlueprint(override val presenter: ItemPresenter<PasswordItemView, PasswordItem>)
    : ItemBlueprint<PasswordItemView, PasswordItem> {

    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.password_item,
            creator = { _, view -> PasswordItemViewHolder(view) }
    )

    override fun isRelevantItem(item: Item) = item is PasswordItem

}