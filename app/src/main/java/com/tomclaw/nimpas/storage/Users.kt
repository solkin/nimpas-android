package com.tomclaw.nimpas.storage

import io.reactivex.Single
import java.io.File

interface Users {

    fun create(): Single<File>

    fun list(): Single<File>

    fun active(): Single<File>

}

class UsersImpl(val dir: File) : Users {

    override fun create(): Single<File> {
        TODO("not implemented")
    }

    override fun list(): Single<File> {
        TODO("not implemented")
    }

    override fun active(): Single<File> {
        TODO("not implemented")
    }

}