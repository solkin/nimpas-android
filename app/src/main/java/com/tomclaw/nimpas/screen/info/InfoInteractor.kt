package com.tomclaw.nimpas.screen.info

import com.tomclaw.nimpas.util.SchedulersFactory

interface InfoInteractor {

}

class InfoInteractorImpl(
        private val schedulers: SchedulersFactory
) : InfoInteractor {

}