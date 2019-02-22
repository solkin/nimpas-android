package com.tomclaw.nimpas.undo

import android.os.Parcel
import android.os.Parcelable

class Undo(
        val id: Long,
        val timeout: Long,
        val message: String
) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeLong(timeout)
        writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Undo> {
        override fun createFromParcel(parcel: Parcel): Undo {
            val id = parcel.readLong()
            val timeout = parcel.readLong()
            val message = parcel.readString().orEmpty()
            return Undo(id, timeout, message)
        }

        override fun newArray(size: Int): Array<Undo?> {
            return arrayOfNulls(size)
        }
    }

}