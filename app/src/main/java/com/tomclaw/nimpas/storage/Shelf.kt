package com.tomclaw.nimpas.storage

import com.tomclaw.drawa.util.safeClose
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Single
import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream

interface Shelf {

    fun createBook(): Single<Book>

    fun listBooks(): Single<Book>

    fun activeBook(): Single<Book>

    fun switchBook(book: Book)

}

class ShelfImpl(
        private val dir: File,
        private val schedulers: SchedulersFactory
) : Shelf {

    private var books: Map<String, Book>? = null
    private var active: String? = null

    override fun createBook(): Single<Book> {
        TODO("not implemented")
    }

    override fun listBooks(): Single<Book> {
        TODO("not implemented")
    }

    override fun activeBook(): Single<Book> {
        TODO("not implemented")
    }

    override fun switchBook(book: Book) {
        TODO("not implemented")
    }

    private fun books(): Single<Map<String, Book>> {
        return books?.let { Single.just(it) } ?: Single.create<Map<String, Book>> { emitter ->
            val books = dir.listFiles()
                    .filter { it.name != CONTENTS_FILE }
                    .associate { it.name to BookImpl(it) }
            emitter.onSuccess(books)
        }.doAfterSuccess { books = it }
    }

    private fun active(): Single<String> {
        return active?.let { Single.just(it) } ?: Single.create<String> { emitter ->
            val shelf = File(dir, CONTENTS_FILE)
            var stream: DataInputStream? = null
            try {
                stream = DataInputStream(FileInputStream(shelf))

                val name = stream.readUTF()

                emitter.onSuccess(name)
            } finally {
                stream.safeClose()
            }
        }.doAfterSuccess { active = it }
    }

}

private const val CONTENTS_FILE = "shelf.dat"