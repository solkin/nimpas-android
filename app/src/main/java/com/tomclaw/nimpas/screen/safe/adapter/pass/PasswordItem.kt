package com.tomclaw.nimpas.screen.safe.adapter.pass

import android.os.Parcel
import android.os.Parcelable
import com.avito.konveyor.blueprint.Item

class PasswordItem(
        override val id: Long,
        val title: String,
        val subtitle: String?,
        val icon: String?
) : Item, Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(title)
        writeString(subtitle)
        writeString(icon)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<PasswordItem> {
        override fun createFromParcel(parcel: Parcel): PasswordItem {
            val id = parcel.readLong()
            val title = parcel.readString().orEmpty()
            val subtitle = parcel.readString()
            val icon = parcel.readString()
            return PasswordItem(id, title, subtitle, icon)
        }

        override fun newArray(size: Int): Array<PasswordItem?> {
            return arrayOfNulls(size)
        }
    }

}