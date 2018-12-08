package com.tomclaw.nimpas.screen.form.adapter.edit

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import com.tomclaw.nimpas.R

class EditItemBlueprint(
        override val presenter: ItemPresenter<EditItemView, EditItem>
) : ItemBlueprint<EditItemView, EditItem> {

    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.edit_item,
            creator = { _, view -> EditItemViewHolder(view) }
    )

    override fun isRelevantItem(item: Item) = item is EditItem

}