package com.tomclaw.nimpas.screen.form.adapter.check

import android.os.Parcel
import android.os.Parcelable
import com.tomclaw.nimpas.screen.form.adapter.FormItem
import com.tomclaw.nimpas.util.readBoolean
import com.tomclaw.nimpas.util.writeBoolean

class CheckItem(
        override val id: Long,
        override val key: String?,
        val text: String,
        var checked: Boolean
) : FormItem {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(key)
        writeString(text)
        writeBoolean(checked)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<CheckItem> {
        override fun createFromParcel(parcel: Parcel): CheckItem {
            val id = parcel.readLong()
            val key = parcel.readString()
            val text = parcel.readString().orEmpty()
            val checked = parcel.readBoolean()
            return CheckItem(id, key, text, checked)
        }

        override fun newArray(size: Int): Array<CheckItem?> {
            return arrayOfNulls(size)
        }
    }

}