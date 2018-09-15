package com.tomclaw.nimpas.journal

import java.io.File

interface Journal {

}

class JournalImpl(private val file: File) : Journal {

}