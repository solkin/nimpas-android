package com.tomclaw.nimpas.screen.form.model

sealed class Widget {

    class Input(val id: Long, val hint: String, val text: String) : Widget()

    class Label(val id: Long, val text: String) : Widget()

    class Check(val id: Long, val text: String, val checked: Boolean) : Widget()

}