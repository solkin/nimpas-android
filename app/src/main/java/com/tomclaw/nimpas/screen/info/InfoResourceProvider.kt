package com.tomclaw.nimpas.screen.info

import android.content.res.Resources
import com.tomclaw.nimpas.R

interface InfoResourceProvider {

    val undoMessage: String

}

class InfoResourceProviderImpl(val resources: Resources) : InfoResourceProvider {

    override val undoMessage = resources.getString(R.string.record_delete_undo_message)

}