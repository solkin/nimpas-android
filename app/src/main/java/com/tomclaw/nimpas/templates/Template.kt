package com.tomclaw.nimpas.templates

import android.os.Parcel
import android.os.Parcelable

class Template(
        val id: String,
        val type: Int? = null,
        val title: String? = null,
        val icon: String? = null,
        val color: String? = null,
        val fields: List<Field>? = null,
        val nested: List<Template>? = null
) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
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
            val id = parcel.readString().orEmpty()
            val type = parcel.readInt().takeIf { it != -1 }
            val title = parcel.readString()
            val icon = parcel.readString()
            val color = parcel.readString()
            val fields = parcel.createTypedArrayList(Field.CREATOR)
            val nested = parcel.createTypedArrayList(Template.CREATOR)
            return Template(id, type, title, icon, color, fields, nested)
        }

        override fun newArray(size: Int): Array<Template?> {
            return arrayOfNulls(size)
        }
    }

}
