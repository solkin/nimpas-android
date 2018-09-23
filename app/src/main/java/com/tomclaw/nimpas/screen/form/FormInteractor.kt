package com.tomclaw.nimpas.screen.form

import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.util.SchedulersFactory

interface FormInteractor {

}

class FormInteractorImpl(
        private val journal: Journal,
        private val schedulers: SchedulersFactory
) : FormInteractor {

}