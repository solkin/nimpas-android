package com.tomclaw.nimpas.screen.book.list.adapter.book

import android.os.Parcel
import android.os.Parcelable
import com.avito.konveyor.blueprint.Item

class BookItem(
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

    companion object CREATOR : Parcelable.Creator<BookItem> {
        override fun createFromParcel(parcel: Parcel): BookItem {
            val id = parcel.readLong()
            val title = parcel.readString().orEmpty()
            val subtitle = parcel.readString()
            return BookItem(id, title, subtitle)
        }

        override fun newArray(size: Int): Array<BookItem?> {
            return arrayOfNulls(size)
        }
    }

}