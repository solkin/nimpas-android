package com.tomclaw.nimpas.screen.info.adapter

import android.os.Parcelable
import com.avito.konveyor.blueprint.Item

interface InfoItem : Item, Parcelable {

    val key: String?

}