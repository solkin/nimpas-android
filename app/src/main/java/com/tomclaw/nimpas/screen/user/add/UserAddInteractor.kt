package com.tomclaw.nimpas.screen.user.add

import com.tomclaw.nimpas.storage.Shelf
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Completable

interface UserAddInteractor {

}

class UserAddInteractorImpl(
        private val shelf: Shelf,
        private val schedulers: SchedulersFactory
) : UserAddInteractor {

}