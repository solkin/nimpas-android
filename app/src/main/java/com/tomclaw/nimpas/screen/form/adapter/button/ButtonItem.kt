package com.tomclaw.nimpas.screen.form.adapter.button

import android.os.Parcel
import android.os.Parcelable
import com.tomclaw.nimpas.screen.form.adapter.FormItem

class ButtonItem(
        override val id: Long,
        override val key: String?,
        val action: String,
        val title: String
) : FormItem {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(key)
        writeString(action)
        writeString(title)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ButtonItem> {
        override fun createFromParcel(parcel: Parcel): ButtonItem {
            val id = parcel.readLong()
            val key = parcel.readString()
            val action = parcel.readString().orEmpty()
            val title = parcel.readString().orEmpty()
            return ButtonItem(id, key, action, title)
        }

        override fun newArray(size: Int): Array<ButtonItem?> {
            return arrayOfNulls(size)
        }
    }

}