package com.tomclaw.nimpas.screen.form.adapter.button

import com.avito.konveyor.blueprint.ItemPresenter
import com.jakewharton.rxrelay2.PublishRelay
import com.tomclaw.nimpas.screen.form.adapter.FormEvent

class ButtonItemPresenter(
        private val events: PublishRelay<FormEvent>
) : ItemPresenter<ButtonItemView, ButtonItem> {

    override fun bindView(view: ButtonItemView, item: ButtonItem, position: Int) {
        view.setTitle(item.title)
        view.setOnClickListener { events.accept(FormEvent.ButtonClicked(item)) }
    }

}