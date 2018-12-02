package com.tomclaw.nimpas.screen.form.adapter.label

import android.os.Parcel
import android.os.Parcelable
import com.avito.konveyor.blueprint.Item

class LabelItem(
        override val id: Long,
        val text: String
) : Item, Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(text)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<LabelItem> {
        override fun createFromParcel(parcel: Parcel): LabelItem {
            val id = parcel.readLong()
            val text = parcel.readString().orEmpty()
            return LabelItem(id, text)
        }

        override fun newArray(size: Int): Array<LabelItem?> {
            return arrayOfNulls(size)
        }
    }

}