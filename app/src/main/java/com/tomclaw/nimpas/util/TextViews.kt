package com.tomclaw.nimpas.util

import android.text.TextUtils
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView

fun TextView.bind(value: String?) {
    if (TextUtils.isEmpty(value)) {
        visibility = GONE
        text = ""
    } else {
        visibility = VISIBLE
        text = value
    }
}