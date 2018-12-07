package com.tomclaw.nimpas.util

import android.os.Parcel

fun Parcel.writeBoolean(value: Boolean) {
    writeInt(if (value) 1 else 0)
}

fun Parcel.readBoolean() = (readInt() == 1)
