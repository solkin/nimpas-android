package com.tomclaw.nimpas.screen.info.di

import android.os.Bundle
import com.tomclaw.nimpas.journal.Record
import dagger.Module

@Module
class InfoModule(
        private val record: Record,
        private val state: Bundle?
) {

}