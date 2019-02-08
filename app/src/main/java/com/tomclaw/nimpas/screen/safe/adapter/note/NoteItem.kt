package com.tomclaw.nimpas.screen.safe.adapter.note

import android.os.Parcel
import android.os.Parcelable
import com.avito.konveyor.blueprint.Item

class NoteItem(
        override val id: Long,
        val title: String,
        val text: String,
        val icon: String?
) : Item, Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(title)
        writeString(text)
        writeString(icon)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<NoteItem> {
        override fun createFromParcel(parcel: Parcel): NoteItem {
            val id = parcel.readLong()
            val title = parcel.readString().orEmpty()
            val text = parcel.readString().orEmpty()
            val icon = parcel.readString()
            return NoteItem(id, title, text, icon)
        }

        override fun newArray(size: Int): Array<NoteItem?> {
            return arrayOfNulls(size)
        }
    }

}