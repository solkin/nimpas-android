package com.tomclaw.nimpas.screen.user.switch

import com.tomclaw.nimpas.storage.Shelf
import com.tomclaw.nimpas.util.SchedulersFactory

interface UserSwitchInteractor {

}

class UserSwitchInteractorImpl(
        private val shelf: Shelf,
        private val schedulers: SchedulersFactory
) : UserSwitchInteractor {

}