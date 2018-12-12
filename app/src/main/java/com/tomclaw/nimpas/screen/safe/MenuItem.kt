package com.tomclaw.nimpas.screen.safe

import android.os.Parcel
import android.os.Parcelable

class MenuItem(
        val id: Long,
        val title: String,
        val icon: String?
) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(title)
        writeString(icon)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<MenuItem> {
        override fun createFromParcel(parcel: Parcel): MenuItem {
            val id = parcel.readLong()
            val text = parcel.readString().orEmpty()
            val icon = parcel.readString()
            return MenuItem(id, text, icon)
        }

        override fun newArray(size: Int): Array<MenuItem?> {
            return arrayOfNulls(size)
        }
    }

}