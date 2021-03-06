package com.tomclaw.nimpas.screen.safe.adapter.card

import android.os.Parcel
import android.os.Parcelable
import com.avito.konveyor.blueprint.Item

class CardItem(
        override val id: Long,
        val title: String,
        val number: String,
        val icon: String?
) : Item, Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(title)
        writeString(number)
        writeString(icon)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<CardItem> {
        override fun createFromParcel(parcel: Parcel): CardItem {
            val id = parcel.readLong()
            val title = parcel.readString().orEmpty()
            val number = parcel.readString().orEmpty()
            val icon = parcel.readString()
            return CardItem(id, title, number, icon)
        }

        override fun newArray(size: Int): Array<CardItem?> {
            return arrayOfNulls(size)
        }
    }

}