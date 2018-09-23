package com.tomclaw.nimpas.screen.lock

import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.util.SchedulersFactory

interface LockInteractor {

}

class LockInteractorImpl(
        private val journal: Journal,
        private val schedulers: SchedulersFactory
) : LockInteractor {

}