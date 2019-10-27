package com.tomclaw.nimpas.screen.safe

import com.tomclaw.nimpas.storage.GROUP_DEFAULT
import com.tomclaw.nimpas.storage.Record
import com.tomclaw.nimpas.storage.Shelf
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Completable
import io.reactivex.Observable
import java.io.File

interface SafeInteractor {

    fun getRecords(groupId: Long = GROUP_DEFAULT): Observable<List<Record>>

    fun getBookFile(): Observable<Pair<File, String>>

    fun lockActiveBook(): Completable

}

class SafeInteractorImpl(
        private val shelf: Shelf,
        private val schedulers: SchedulersFactory
) : SafeInteractor {

    override fun getRecords(groupId: Long): Observable<List<Record>> {
        return shelf.activeBook()
                .flatMap { it.getRecords(groupId) }
                .toObservable()
                .subscribeOn(schedulers.io())
    }

    override fun getBookFile(): Observable<Pair<File, String>> {
        return shelf.activeBook()
                .map { Pair(it.getFile(), it.getTitle()) }
                .toObservable()
                .subscribeOn(schedulers.io())
    }

    override fun lockActiveBook(): Completable {
        return shelf.activeBook()
                .map { it.lock() }
                .ignoreElement()
                .subscribeOn(schedulers.io())
    }

}