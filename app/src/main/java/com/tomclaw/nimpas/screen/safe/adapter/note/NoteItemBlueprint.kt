package com.tomclaw.nimpas.screen.safe.adapter.note

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import com.tomclaw.nimpas.R

class NoteItemBlueprint(override val presenter: ItemPresenter<NoteItemView, NoteItem>)
    : ItemBlueprint<NoteItemView, NoteItem> {

    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.note_item,
            creator = { _, view -> NoteItemViewHolder(view) }
    )

    override fun isRelevantItem(item: Item) = item is NoteItem

}