package com.tomclaw.nimpas.screen.form.model

sealed class Widget {

    class Edit(val id: Long, val hint: String, val text: String) : Widget()

    class Header(val id: Long, val text: String) : Widget()

    class Check(val id: Long, val text: String, val checked: Boolean) : Widget()

}