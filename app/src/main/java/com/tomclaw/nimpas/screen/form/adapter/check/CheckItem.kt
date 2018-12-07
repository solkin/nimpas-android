package com.tomclaw.nimpas.screen.form.adapter.check

import android.os.Parcel
import android.os.Parcelable
import com.avito.konveyor.blueprint.Item
import com.tomclaw.nimpas.util.readBoolean
import com.tomclaw.nimpas.util.writeBoolean

class CheckItem(
        override val id: Long,
        val text: String,
        val checked: Boolean
) : Item, Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(text)
        writeBoolean(checked)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<CheckItem> {
        override fun createFromParcel(parcel: Parcel): CheckItem {
            val id = parcel.readLong()
            val text = parcel.readString().orEmpty()
            val checked = parcel.readBoolean()
            return CheckItem(id, text, checked)
        }

        override fun newArray(size: Int): Array<CheckItem?> {
            return arrayOfNulls(size)
        }
    }

}