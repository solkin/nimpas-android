package com.tomclaw.nimpas.screen.form.adapter.edit

import android.os.Parcel
import android.os.Parcelable
import com.avito.konveyor.blueprint.Item

class EditItem(
        override val id: Long,
        val hint: String,
        val text: String
) : Item, Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(hint)
        writeString(text)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<EditItem> {
        override fun createFromParcel(parcel: Parcel): EditItem {
            val id = parcel.readLong()
            val hint = parcel.readString().orEmpty()
            val text = parcel.readString().orEmpty()
            return EditItem(id, hint, text)
        }

        override fun newArray(size: Int): Array<EditItem?> {
            return arrayOfNulls(size)
        }
    }

}