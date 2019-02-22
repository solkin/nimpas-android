package com.tomclaw.nimpas.undo

import io.reactivex.Completable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit.SECONDS
import java.util.concurrent.atomic.AtomicLong

interface Undoer {

    fun handleAction(timeout: Long, action: Completable): Long

    fun handleAction(action: UndoAction): Long

    fun invokeUndo(id: Long): Completable?

}

class UndoerImpl : Undoer {

    private val executor = Executors.newScheduledThreadPool(0)

    private val undoCords = HashMap<Long, UndoAction>()

    private val counter = AtomicLong()

    override fun handleAction(timeout: Long, action: Completable): Long {
        return handleAction(object : UndoAction {

            override val timeout: Long
                get() = timeout

            override operator fun invoke() = action

        })
    }

    override fun handleAction(action: UndoAction): Long {
        return counter.incrementAndGet().also { id ->
            executor.schedule({
                undoCords.remove(id)
            }, action.timeout, SECONDS)
            undoCords += id to action
        }
    }

    override fun invokeUndo(id: Long): Completable? {
        return undoCords.remove(id)?.invoke()
    }

}
