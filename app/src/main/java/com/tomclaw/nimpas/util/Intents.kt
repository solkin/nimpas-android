package com.tomclaw.nimpas.util

import android.content.Intent
import com.tomclaw.nimpas.undo.Undo

fun Intent.putUndo(undo: Undo) = putExtra(EXTRA_UNDO, undo)

fun Intent.getUndo(): Undo? = getParcelableExtra(EXTRA_UNDO)

const val EXTRA_UNDO = "undo"