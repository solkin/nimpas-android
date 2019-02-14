package com.tomclaw.nimpas.screen.info.adapter.header

import android.os.Parcel
import android.os.Parcelable
import com.tomclaw.nimpas.screen.info.adapter.InfoItem

class HeaderItem(
        override val id: Long,
        override val key: String?,
        val title: String
) : InfoItem {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(key)
        writeString(title)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<HeaderItem> {
        override fun createFromParcel(parcel: Parcel): HeaderItem {
            val id = parcel.readLong()
            val key = parcel.readString()
            val text = parcel.readString().orEmpty()
            return HeaderItem(id, key, text)
        }

        override fun newArray(size: Int): Array<HeaderItem?> {
            return arrayOfNulls(size)
        }
    }

}