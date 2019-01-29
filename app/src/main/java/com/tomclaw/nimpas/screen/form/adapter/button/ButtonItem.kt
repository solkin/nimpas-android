package com.tomclaw.nimpas.screen.form.adapter.button

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.ColorInt
import com.avito.konveyor.blueprint.Item

class ButtonItem(
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

    companion object CREATOR : Parcelable.Creator<ButtonItem> {
        override fun createFromParcel(parcel: Parcel): ButtonItem {
            val id = parcel.readLong()
            val text = parcel.readString().orEmpty()
            val icon = parcel.readString()
            val color = parcel.readInt().takeIf { it != -1 }
            return ButtonItem(id, text, icon, color)
        }

        override fun newArray(size: Int): Array<ButtonItem?> {
            return arrayOfNulls(size)
        }
    }

}