package com.tomclaw.nimpas.screen.safe.adapter.item

import android.os.Parcel
import android.os.Parcelable
import com.avito.konveyor.blueprint.Item

class NoteItem(
        override val id: Long,
        val title: String,
        val text: String
) : Item, Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(title)
        writeString(text)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<NoteItem> {
        override fun createFromParcel(parcel: Parcel): NoteItem {
            val id = parcel.readLong()
            val title = parcel.readString().orEmpty()
            val text = parcel.readString().orEmpty()
            return NoteItem(id, title, text)
        }

        override fun newArray(size: Int): Array<NoteItem?> {
            return arrayOfNulls(size)
        }
    }

}