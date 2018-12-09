package com.tomclaw.nimpas.templates

import io.reactivex.Observable

interface TemplatesInteractor {

    fun getTemplates(): Observable<List<Template>>

}

class SafeInteractorImpl(
        private val journal: Journal,
        private val schedulers: SchedulersFactory
) : SafeInteractor {

    override fun getRecords(groupId: Long): Observable<List<Record>> {
        return journal.getRecords(groupId)
                .toObservable()
                .subscribeOn(schedulers.io())
    }

}