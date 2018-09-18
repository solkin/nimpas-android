package com.tomclaw.nimpas.screen.safe.adapter.pass

import android.os.Parcel
import android.os.Parcelable
import com.avito.konveyor.blueprint.Item

class PassItem(
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

    companion object CREATOR : Parcelable.Creator<PassItem> {
        override fun createFromParcel(parcel: Parcel): PassItem {
            val id = parcel.readLong()
            val image = parcel.readInt()
            val title = parcel.readString().orEmpty()
            val subtitle = parcel.readString()
            return PassItem(id, image, title, subtitle)
        }

        override fun newArray(size: Int): Array<PassItem?> {
            return arrayOfNulls(size)
        }
    }

}