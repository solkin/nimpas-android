package com.tomclaw.nimpas.templates

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Template(
        @SerializedName("id") val id: Long,
        @SerializedName("version") val version: Int = 0,
        @SerializedName("type") val type: Int? = null,
        @SerializedName("title") val title: String? = null,
        @SerializedName("icon") val icon: String? = null,
        @SerializedName("color") val color: String? = null,
        @SerializedName("fields") val fields: List<Field>? = null,
        @SerializedName("nested") val nested: List<Template>? = null
) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeInt(version)
        writeInt(type ?: -1)
        writeString(title)
        writeString(icon)
        writeString(color)
        writeTypedList(fields)
        writeTypedList(nested)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Template> {
        override fun createFromParcel(parcel: Parcel): Template {
            val id = parcel.readLong()
            val version = parcel.readInt()
            val type = parcel.readInt().takeIf { it != -1 }
            val title = parcel.readString()
            val icon = parcel.readString()
            val color = parcel.readString()
            val fields = parcel.createTypedArrayList(Field.CREATOR)
            val nested = parcel.createTypedArrayList(Template.CREATOR)
            return Template(id, version, type, title, icon, color, fields, nested)
        }

        override fun newArray(size: Int): Array<Template?> {
            return arrayOfNulls(size)
        }
    }

}

const val TYPE_GROUP = 1
const val TYPE_PASSWORD = 2
const val TYPE_CARD = 3
const val TYPE_NOTE = 4
