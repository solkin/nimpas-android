package com.tomclaw.nimpas.screen.form.adapter

import com.tomclaw.nimpas.screen.form.adapter.action.ActionItem
import com.tomclaw.nimpas.screen.form.adapter.button.ButtonItem
import com.tomclaw.nimpas.screen.form.adapter.edit.EditItem

sealed class FormEvent {

    class EditChanged(val item: EditItem, text: String) : FormEvent()

    class ActionClicked(val item: ActionItem) : FormEvent()

    class ButtonClicked(val item: ButtonItem) : FormEvent()

}