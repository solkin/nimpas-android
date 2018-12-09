package com.tomclaw.nimpas.screen.form.adapter.action

import android.graphics.PorterDuff
import android.support.annotation.ColorInt
import android.support.v7.content.res.AppCompatResources
import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.ItemView
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGImageView
import com.tomclaw.nimpas.R
import com.tomclaw.nimpas.util.bind

interface ActionItemView : ItemView {

    fun setTitle(text: String)

    fun setIcon(svg: String, @ColorInt color: Int)

}

class ActionItemViewHolder(view: View) : BaseViewHolder(view), ActionItemView {

    private val context = view.context

    private val title: TextView = view.findViewById(R.id.title)
    private val circle: View = view.findViewById(R.id.circle)
    private val icon: SVGImageView = view.findViewById(R.id.icon)

    override fun setTitle(text: String) {
        title.bind(text)
    }

    override fun setIcon(svg: String, @ColorInt color: Int) {
        val backDrawable = AppCompatResources.getDrawable(context, R.drawable.circle_back)
        if (backDrawable != null) {
            backDrawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
            circle.setBackgroundDrawable(backDrawable)
        }
        icon.setSVG(SVG.getFromString(svg))
    }

}