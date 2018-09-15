package com.tomclaw.nimpas.screen.safe

import com.avito.konveyor.blueprint.Item
import com.tomclaw.nimpas.journal.Record

interface RecordConverter {

    fun convert(record: Record): Item

}

class RecordConverterImpl() : RecordConverter {

    override fun convert(record: Record): Item {
        TODO("not implemented")
    }

}