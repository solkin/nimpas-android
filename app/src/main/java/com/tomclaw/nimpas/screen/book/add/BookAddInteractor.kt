package com.tomclaw.nimpas.screen.book.add

import com.tomclaw.nimpas.storage.Shelf
import com.tomclaw.nimpas.util.SchedulersFactory

interface BookAddInteractor {

}

class BookAddInteractorImpl(
        private val shelf: Shelf,
        private val schedulers: SchedulersFactory
) : BookAddInteractor {

}