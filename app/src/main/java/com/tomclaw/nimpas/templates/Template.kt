package com.tomclaw.nimpas.templates

import android.os.Parcel
import android.os.Parcelable
import com.avito.konveyor.blueprint.Item

class Template(
        val id: String,
        val type: Int?,
        val title: String?,
        val icon: String?,
        val color: Int?,
        val fields: Int?,
        val nested: Int?
) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(title)
        writeString(subtitle)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<PasswordItem> {
        override fun createFromParcel(parcel: Parcel): PasswordItem {
            val id = parcel.readLong()
            val title = parcel.readString().orEmpty()
            val subtitle = parcel.readString()
            return PasswordItem(id, title, subtitle)
        }

        override fun newArray(size: Int): Array<PasswordItem?> {
            return arrayOfNulls(size)
        }
    }

}
