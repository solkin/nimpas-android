package com.tomclaw.nimpas.util

import java.util.ArrayList
import java.util.Collections.emptyList

class DataProvider<A> {

    private var data: List<A> = emptyList()

    fun getItem(position: Int): A = data[position]

    fun size() = data.size

    fun setData(data: List<A>) {
        this.data = ArrayList(data)
    }

}
