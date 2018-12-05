package com.tomclaw.nimpas.screen.form.adapter

sealed class FormEvent {

    class InputChanged(text: String) : FormEvent()

}