package com.tomclaw.nimpas.screen.safe.adapter.group

import android.os.Parcel
import android.os.Parcelable
import com.avito.konveyor.blueprint.Item

class GroupItem(
        override val id: Long,
        val title: String,
        val icon: String?
) : Item, Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(title)
        writeString(icon)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<GroupItem> {
        override fun createFromParcel(parcel: Parcel): GroupItem {
            val id = parcel.readLong()
            val title = parcel.readString().orEmpty()
            val icon = parcel.readString()
            return GroupItem(id, title, icon)
        }

        override fun newArray(size: Int): Array<GroupItem?> {
            return arrayOfNulls(size)
        }
    }

}