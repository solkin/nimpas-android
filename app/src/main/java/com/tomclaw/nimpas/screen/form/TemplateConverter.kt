package com.tomclaw.nimpas.screen.form

import android.graphics.Color
import com.avito.konveyor.blueprint.Item
import com.tomclaw.nimpas.screen.form.adapter.action.ActionItem
import com.tomclaw.nimpas.templates.Template

interface TemplateConverter {

    fun convert(template: Template): Item

}

class TemplateConverterImpl : TemplateConverter {

    override fun convert(template: Template): Item {
        return ActionItem(
                id = template.id,
                title = template.title.orEmpty(),
                icon = template.icon,
                color = Color.parseColor(template.color)
        )
    }

}
