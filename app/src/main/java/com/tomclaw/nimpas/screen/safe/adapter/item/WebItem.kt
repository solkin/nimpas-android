package com.tomclaw.nimpas.screen.safe.adapter.item

import android.os.Parcel
import android.os.Parcelable
import com.avito.konveyor.blueprint.Item

class WebItem(
        override val id: Long,
        val image: Int,
        val title: String,
        val subtitle: String?
) : Item, Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeInt(image)
        writeString(title)
        writeString(subtitle)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<WebItem> {
        override fun createFromParcel(parcel: Parcel): WebItem {
            val id = parcel.readLong()
            val image = parcel.readInt()
            val title = parcel.readString().orEmpty()
            val subtitle = parcel.readString()
            return WebItem(id, image, title, subtitle)
        }

        override fun newArray(size: Int): Array<WebItem?> {
            return arrayOfNulls(size)
        }
    }

}