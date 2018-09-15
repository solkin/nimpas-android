package com.tomclaw.nimpas.screen.safe.adapter.item

import android.os.Parcel
import android.os.Parcelable
import com.avito.konveyor.blueprint.Item

class GroupItem(
        override val id: Long,
        val title: String
) : Item, Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(title)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<GroupItem> {
        override fun createFromParcel(parcel: Parcel): GroupItem {
            val id = parcel.readLong()
            val title = parcel.readString().orEmpty()
            return GroupItem(id, title)
        }

        override fun newArray(size: Int): Array<GroupItem?> {
            return arrayOfNulls(size)
        }
    }

}