package com.tomclaw.drawa.util

import java.io.Closeable
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException

fun Closeable?.safeClose() {
    try {
        this?.close()
    } catch (ignored: IOException) {
    }
}

fun DataOutputStream.writeNullableUTF(str: String?) {
    writeNullable(str) { writeUTF(str) }
}

fun DataOutputStream.writeNullableInt(int: Int?) {
    writeNullable(int) { writeInt(it) }
}

fun DataInputStream.readNullableUTF(): String? {
    return readNullable { readUTF() }
}

fun DataInputStream.readNullableInt(): Int? {
    return readNullable { readInt() }
}

fun <T> DataOutputStream.writeNullable(value: T?, action: (T) -> Unit) {
    writeBoolean(value != null)
    if (value != null) {
        action.invoke(value)
    }
}

fun <T> DataInputStream.readNullable(action: () -> T): T? {
    val isNotNull = readBoolean()
    return if (isNotNull) {
        action.invoke()
    } else {
        null
    }
}