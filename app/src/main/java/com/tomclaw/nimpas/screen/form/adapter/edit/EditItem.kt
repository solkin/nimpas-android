package com.tomclaw.nimpas.screen.form.adapter.edit

import android.os.Parcel
import android.os.Parcelable
import com.tomclaw.nimpas.screen.form.adapter.FormItem

class EditItem(
        override val id: Long,
        override val key: String?,
        val hint: String,
        var text: String
) : FormItem {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(key)
        writeString(hint)
        writeString(text)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<EditItem> {
        override fun createFromParcel(parcel: Parcel): EditItem {
            val id = parcel.readLong()
            val key = parcel.readString()
            val hint = parcel.readString().orEmpty()
            val text = parcel.readString().orEmpty()
            return EditItem(id, key, hint, text)
        }

        override fun newArray(size: Int): Array<EditItem?> {
            return arrayOfNulls(size)
        }
    }

}