package com.tomclaw.nimpas.screen.form.converter

import android.graphics.Color
import com.tomclaw.nimpas.screen.form.adapter.FormItem
import com.tomclaw.nimpas.screen.form.adapter.action.ActionItem
import com.tomclaw.nimpas.templates.Template

interface TemplateConverter {

    fun convert(template: Template): FormItem

}

class TemplateConverterImpl : TemplateConverter {

    override fun convert(template: Template): FormItem {
        return ActionItem(
                id = template.id,
                key = template.id.toString(),
                title = template.title.orEmpty(),
                icon = template.icon,
                color = Color.parseColor(template.color)
        )
    }

}
