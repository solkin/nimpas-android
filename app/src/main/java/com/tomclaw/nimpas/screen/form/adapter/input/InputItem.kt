package com.tomclaw.nimpas.screen.form.adapter.input

import android.os.Parcel
import android.os.Parcelable
import com.avito.konveyor.blueprint.Item

class InputItem(
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

    companion object CREATOR : Parcelable.Creator<InputItem> {
        override fun createFromParcel(parcel: Parcel): InputItem {
            val id = parcel.readLong()
            val hint = parcel.readString().orEmpty()
            val text = parcel.readString().orEmpty()
            return InputItem(id, hint, text)
        }

        override fun newArray(size: Int): Array<InputItem?> {
            return arrayOfNulls(size)
        }
    }

}