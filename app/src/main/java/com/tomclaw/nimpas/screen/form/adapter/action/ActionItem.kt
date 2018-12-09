package com.tomclaw.nimpas.screen.form.adapter.action

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.ColorInt
import com.avito.konveyor.blueprint.Item

class ActionItem(
        override val id: Long,
        val title: String,
        val icon: String?,
        @ColorInt val color: Int?
) : Item, Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(title)
        writeString(icon)
        writeInt(color ?: -1)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ActionItem> {
        override fun createFromParcel(parcel: Parcel): ActionItem {
            val id = parcel.readLong()
            val text = parcel.readString().orEmpty()
            val icon = parcel.readString()
            val color = parcel.readInt().takeIf { it != -1 }
            return ActionItem(id, text, icon, color)
        }

        override fun newArray(size: Int): Array<ActionItem?> {
            return arrayOfNulls(size)
        }
    }

}