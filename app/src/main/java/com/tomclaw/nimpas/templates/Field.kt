package com.tomclaw.nimpas.templates

import android.os.Parcel
import android.os.Parcelable

class Field(
        val type: String,
        val key: String?,
        val title: String,
        val params: List<String>?
) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(title)
        writeString(text)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Field> {
        override fun createFromParcel(parcel: Parcel): Field {
            val id = parcel.readLong()
            val title = parcel.readString().orEmpty()
            val text = parcel.readString().orEmpty()
            return Field(id, title, text)
        }

        override fun newArray(size: Int): Array<Field?> {
            return arrayOfNulls(size)
        }
    }

}
