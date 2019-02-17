package com.tomclaw.nimpas.screen.info.adapter.text

import android.os.Parcel
import android.os.Parcelable
import com.tomclaw.nimpas.screen.info.adapter.InfoItem

class TextItem(
        override val id: Long,
        override val key: String?,
        val text: String?,
        val hint: String
) : InfoItem {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(key)
        writeString(text)
        writeString(hint)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<TextItem> {
        override fun createFromParcel(parcel: Parcel): TextItem {
            val id = parcel.readLong()
            val key = parcel.readString()
            val text = parcel.readString()
            val hint = parcel.readString().orEmpty()
            return TextItem(id, key, text, hint)
        }

        override fun newArray(size: Int): Array<TextItem?> {
            return arrayOfNulls(size)
        }
    }

}