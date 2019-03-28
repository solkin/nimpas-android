package com.tomclaw.nimpas.screen.user.list.adapter.user

import android.os.Parcel
import android.os.Parcelable
import com.avito.konveyor.blueprint.Item

class UserItem(
        override val id: Long,
        val title: String,
        val subtitle: String?
) : Item, Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(title)
        writeString(subtitle)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<UserItem> {
        override fun createFromParcel(parcel: Parcel): UserItem {
            val id = parcel.readLong()
            val title = parcel.readString().orEmpty()
            val subtitle = parcel.readString()
            return UserItem(id, title, subtitle)
        }

        override fun newArray(size: Int): Array<UserItem?> {
            return arrayOfNulls(size)
        }
    }

}