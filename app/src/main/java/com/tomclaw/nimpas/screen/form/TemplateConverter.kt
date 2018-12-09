package com.tomclaw.nimpas.screen.form

import com.avito.konveyor.blueprint.Item
import com.tomclaw.nimpas.screen.form.adapter.group.GroupItem
import com.tomclaw.nimpas.templates.Template

interface TemplateConverter {

    fun convert(template: Template): Item

}

class TemplateConverterImpl : TemplateConverter {

    override fun convert(template: Template): Item {
        return GroupItem(template.id, template.title.orEmpty())
    }

//    override fun convert(widget: Widget): Item = when (widget) {
//        is Widget.Group -> GroupItem(widget.id, widget.text)
//        is Widget.Edit -> EditItem(widget.id, widget.hint, widget.text)
//        is Widget.Header -> HeaderItem(widget.id, widget.text)
//        is Widget.Check -> CheckItem(widget.id, widget.text, widget.checked)
//    }

}