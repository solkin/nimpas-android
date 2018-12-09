package com.tomclaw.nimpas.templates

import android.os.Parcel
import android.os.Parcelable
import java.util.HashMap

class Field(
        val type: String,
        val key: String?,
        val params: Map<String, String>?
) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int): Unit = with(dest) {
        writeString(type)
        writeString(key)
        dest.writeInt(params?.size ?: -1)
        params?.let {
            for (entry in params.entries) {
                dest.writeString(entry.key)
                dest.writeString(entry.value)
            }
        }
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Field> {
        override fun createFromParcel(parcel: Parcel): Field {
            val type = parcel.readString().orEmpty()
            val key = parcel.readString()
            val size = parcel.readInt()
            val params: Map<String, String>? = when {
                size > 0 -> {
                    HashMap<String, String>().apply {
                        for (i in 0 until size) {
                            val paramKey = parcel.readString().orEmpty()
                            val paramValue = parcel.readString().orEmpty()
                            this[paramKey] = paramValue
                        }
                    }
                }
                size == 0 -> emptyMap()
                else -> null
            }
            return Field(type, key, params)
        }

        override fun newArray(size: Int): Array<Field?> {
            return arrayOfNulls(size)
        }
    }

}
