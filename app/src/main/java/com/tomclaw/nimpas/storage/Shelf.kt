package com.tomclaw.nimpas.storage

import com.tomclaw.drawa.util.safeClose
import com.tomclaw.nimpas.util.SchedulersFactory
import com.tomclaw.nimpas.util.sha256
import io.reactivex.Completable
import io.reactivex.Single
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

interface Shelf {

    fun createBook(): Single<Book>

    fun listBooks(): Single<Map<String, Book>>

    fun activeBook(): Single<Book>

    fun switchBook(id: String): Completable

}

class ShelfImpl(
        private val dir: File,
        private val schedulers: SchedulersFactory
) : Shelf {

    private var books: Map<String, Book>? = null
    private var activeId: String? = null

    override fun createBook(): Single<Book> = books()
            .map {
                val id = generateId(it.keys)
                val file = File(dir, id).apply {
                    if (exists()) delete()
                    createNewFile()
                }
                val book: Book = BookImpl(file)
                books = it + (id to book)
                book
            }
            .subscribeOn(schedulers.io())

    override fun listBooks(): Single<Map<String, Book>> = books()
            .subscribeOn(schedulers.io())

    override fun activeBook(): Single<Book> = activeBookId()
            .flatMap { books() }
            .map { it[activeId] ?: throw NoActiveBookException() }
            .subscribeOn(schedulers.io())

    override fun switchBook(id: String): Completable = saveActiveBookId(id)
            .andThen { activeId = id }
            .subscribeOn(schedulers.io())

    private fun books(): Single<Map<String, Book>> {
        return books?.let { Single.just(it) } ?: Single.create<Map<String, Book>> { emitter ->
            val books = dir.listFiles()
                    .filter { it.name != CONTENTS_FILE }
                    .associate { it.name to BookImpl(it) }
            this.books = books
            emitter.onSuccess(books)
        }
    }

    private fun activeBookId(): Single<String> {
        return activeId?.let { Single.just(it) } ?: Single.create<String> { emitter ->
            readActiveBookId()
                    ?.let {
                        activeId = it
                        emitter.onSuccess(it)
                    }
                    ?: emitter.onError(NoActiveBookException())
        }
    }

    private fun generateId(ids: Set<String>): String {
        var id: String
        do {
            id = sha256(ids.joinToString() + System.currentTimeMillis())
        } while (ids.contains(id))
        return id
    }

    private fun saveActiveBookId(id: String): Completable {
        return Completable.create { emitter ->
            writeActiveBookId(id).takeIf { true }
                    ?.run { emitter.onComplete() }
                    ?: emitter.onError(IOException("Failed to assign active book"))
        }.doOnComplete { activeId = id }
    }

    private fun readActiveBookId(): String? {
        val shelf = File(dir, CONTENTS_FILE)
        var stream: DataInputStream? = null
        return try {
            stream = DataInputStream(FileInputStream(shelf))
            stream.readUTF()
        } catch (ex: Throwable) {
            null
        } finally {
            stream.safeClose()
        }
    }

    private fun writeActiveBookId(id: String): Boolean {
        val shelf = File(dir, CONTENTS_FILE)
        var stream: DataOutputStream? = null
        return try {
            stream = DataOutputStream(FileOutputStream(shelf))
            stream.writeUTF(id)
            true
        } catch (ex: Throwable) {
            false
        } finally {
            stream.safeClose()
        }
    }

    class NoActiveBookException : Exception()

}

private const val CONTENTS_FILE = "shelf.dat"