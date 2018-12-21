package com.tomclaw.nimpas.screen.form.adapter.action

import com.avito.konveyor.blueprint.ItemPresenter
import com.jakewharton.rxrelay2.PublishRelay
import com.tomclaw.nimpas.screen.form.adapter.FormEvent

class ActionItemPresenter(
        private val events: PublishRelay<FormEvent>
) : ItemPresenter<ActionItemView, ActionItem> {

    override fun bindView(view: ActionItemView, item: ActionItem, position: Int) {
        view.setTitle(item.title)
        if (item.icon != null && item.color != null) {
            view.setIcon(item.icon, item.color)
        }
        view.setOnClickListener { events.accept(FormEvent.ActionClicked(item)) }
    }

}