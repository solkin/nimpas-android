package com.tomclaw.nimpas.undo

import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit.SECONDS
import java.util.concurrent.atomic.AtomicLong

interface Undoer {

    fun handleAction(action: UndoAction): Long

    fun invokeUndo(id: Long)

}

class UndoerImpl(private val schedulers: SchedulersFactory) : Undoer {

    private val executor = Executors.newScheduledThreadPool(0)

    private val undoCords = HashMap<Long, Pair<UndoAction, Future<*>>>()

    private val counter = AtomicLong()
    private val subscriptions = CompositeDisposable()

    override fun handleAction(action: UndoAction): Long {
        return counter.incrementAndGet().also { id ->
            undoCords += id to (action to executor.schedule({
                undoCords.remove(id)
            }, action.timeout, SECONDS))
        }
    }

    override fun invokeUndo(id: Long) {
        undoCords[id]?.let { pair ->
            val undo = pair.first
            val future = pair.second
            future.cancel(true)
            subscriptions += undo.invoke()
                    .observeOn(schedulers.mainThread())
                    .subscribe()
        }
    }

}
