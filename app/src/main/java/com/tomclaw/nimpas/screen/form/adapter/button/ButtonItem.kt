package com.tomclaw.nimpas.screen.form.adapter.button

import android.os.Parcel
import android.os.Parcelable
import com.avito.konveyor.blueprint.Item

class ButtonItem(
        override val id: Long,
        val action: String,
        val title: String
) : Item, Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(action)
        writeString(title)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ButtonItem> {
        override fun createFromParcel(parcel: Parcel): ButtonItem {
            val id = parcel.readLong()
            val action = parcel.readString().orEmpty()
            val title = parcel.readString().orEmpty()
            return ButtonItem(id, action, title)
        }

        override fun newArray(size: Int): Array<ButtonItem?> {
            return arrayOfNulls(size)
        }
    }

}