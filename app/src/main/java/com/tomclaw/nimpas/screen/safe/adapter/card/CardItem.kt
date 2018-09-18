package com.tomclaw.nimpas.screen.safe.adapter.card

import android.os.Parcel
import android.os.Parcelable
import com.avito.konveyor.blueprint.Item

class CardItem(
        override val id: Long,
        val image: Int,
        val title: String,
        val number: String
) : Item, Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeInt(image)
        writeString(title)
        writeString(number)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<CardItem> {
        override fun createFromParcel(parcel: Parcel): CardItem {
            val id = parcel.readLong()
            val image = parcel.readInt()
            val title = parcel.readString().orEmpty()
            val number = parcel.readString().orEmpty()
            return CardItem(id, image, title, number)
        }

        override fun newArray(size: Int): Array<CardItem?> {
            return arrayOfNulls(size)
        }
    }

}