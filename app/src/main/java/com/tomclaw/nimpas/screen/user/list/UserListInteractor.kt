package com.tomclaw.nimpas.screen.user.list

import com.tomclaw.nimpas.storage.Shelf
import com.tomclaw.nimpas.util.SchedulersFactory

interface UserListInteractor {

}

class UserListInteractorImpl(
        private val shelf: Shelf,
        private val schedulers: SchedulersFactory
) : UserListInteractor {

}