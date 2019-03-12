package com.tomclaw.nimpas.storage

import com.tomclaw.drawa.util.safeClose
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Completable
import io.reactivex.Single
import java.io.*

interface Shelf {

    fun createBook(): Single<Book>

    fun listBooks(): Single<Map<String, Book>>

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

    override fun listBooks(): Single<Map<String, Book>> {
        return books()
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

    private fun activeBookName(): Single<String> {
        return active?.let { Single.just(it) } ?: Single.create<String> { emitter ->
            readActiveBookName()
                    ?.let { emitter.onSuccess(it) }
                    ?: emitter.onError(Exception("No active book"))
        }.doAfterSuccess { active = it }
    }

    private fun saveActiveBookName(name: String): Completable {
        return Completable.create { emitter ->
            writeActiveBookName(name).takeIf { true }
                    ?.run { emitter.onComplete() }
                    ?: emitter.onError(Exception("Failed to assign active book"))
        }.doOnComplete { active = name }
    }

    private fun readActiveBookName(): String? {
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

    private fun writeActiveBookName(name: String): Boolean {
        val shelf = File(dir, CONTENTS_FILE)
        var stream: DataOutputStream? = null
        return try {
            stream = DataOutputStream(FileOutputStream(shelf))
            stream.writeUTF(name)
            true
        } catch (ex: Throwable) {
            false
        } finally {
            stream.safeClose()
        }
    }

}

private const val CONTENTS_FILE = "shelf.dat"