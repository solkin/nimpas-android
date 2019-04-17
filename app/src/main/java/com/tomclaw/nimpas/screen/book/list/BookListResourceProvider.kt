package com.tomclaw.nimpas.screen.book.list

import android.annotation.SuppressLint
import android.content.res.Resources
import com.tomclaw.nimpas.R
import java.text.SimpleDateFormat
import java.util.Locale

interface BookListResourceProvider {

    fun formatDate(time: Long): String

}

class BookListResourceProviderImpl(val resources: Resources) : BookListResourceProvider {

    @SuppressLint("ConstantLocale")
    private val simpleDateFormat = SimpleDateFormat("EEE, MMM d, ''yy", Locale.getDefault())

    override fun formatDate(time: Long): String {
        val date = simpleDateFormat.format(time)
        return resources.getString(R.string.date_updated, date)
    }

}