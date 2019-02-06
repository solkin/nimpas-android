package com.tomclaw.nimpas.journal

import android.os.Parcel
import android.os.Parcelable
import com.tomclaw.nimpas.templates.Template
import java.util.HashMap

class Record(
        val id: Long,
        val groupId: Long,
        val time: Long,
        val template: Template,
        val fields: Map<String, String>
) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeLong(groupId)
        writeLong(time)
        writeParcelable(template, flags)
        dest.writeInt(fields.size)
        fields.let {
            for (entry in fields.entries) {
                dest.writeString(entry.key)
                dest.writeString(entry.value)
            }
        }
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Record> {
        override fun createFromParcel(parcel: Parcel): Record {
            val id = parcel.readLong()
            val groupId = parcel.readLong()
            val time = parcel.readLong()
            val template = parcel.readParcelable<Template>(Template::class.java.classLoader)!!
            val size = parcel.readInt()
            val fields: Map<String, String> = when {
                size > 0 -> {
                    HashMap<String, String>().apply {
                        for (i in 0 until size) {
                            val paramKey = parcel.readString().orEmpty()
                            val paramValue = parcel.readString().orEmpty()
                            this[paramKey] = paramValue
                        }
                    }
                }
                else -> emptyMap()
            }
            return Record(id, groupId, time, template, fields)
        }

        override fun newArray(size: Int): Array<Record?> {
            return arrayOfNulls(size)
        }
    }

}

const val GROUP_DEFAULT = 0L
