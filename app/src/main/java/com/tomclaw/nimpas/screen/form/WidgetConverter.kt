package com.tomclaw.nimpas.screen.form

import com.avito.konveyor.blueprint.Item
import com.tomclaw.nimpas.screen.form.adapter.check.CheckItem
import com.tomclaw.nimpas.screen.form.adapter.edit.EditItem
import com.tomclaw.nimpas.screen.form.adapter.group.GroupItem
import com.tomclaw.nimpas.screen.form.adapter.header.HeaderItem
import com.tomclaw.nimpas.screen.form.model.Widget

interface WidgetConverter {

    fun convert(widget: Widget): Item

}

class WidgetConverterImpl : WidgetConverter {

    override fun convert(widget: Widget): Item = when (widget) {
        is Widget.Group -> GroupItem(widget.id, widget.text)
        is Widget.Edit -> EditItem(widget.id, widget.hint, widget.text)
        is Widget.Header -> HeaderItem(widget.id, widget.text)
        is Widget.Check -> CheckItem(widget.id, widget.text, widget.checked)
    }

}