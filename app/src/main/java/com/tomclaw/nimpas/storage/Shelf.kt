package com.tomclaw.nimpas.storage

import android.content.ContentResolver
import android.net.Uri
import com.tomclaw.drawa.util.safeClose
import com.tomclaw.nimpas.util.SchedulersFactory
import com.tomclaw.nimpas.util.sha1
import io.reactivex.Completable
import io.reactivex.Single
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.Collections

interface Shelf {

    fun createBook(keyword: String, title: String): Single<String>

    fun importBook(uri: Uri): Single<String>

    fun listBooks(): Single<Map<String, Book>>

    fun activeBook(): Single<Book>

    fun switchBook(id: String): Completable

    fun deleteBook(id: String): Completable

}

class NoActiveBookException : Exception()

class ShelfImpl(
        private val dir: File,
        private val contentResolver: ContentResolver,
        private val schedulers: SchedulersFactory
) : Shelf {

    private var books: MutableMap<String, Book>? = null
    private var activeId: String? = null

    override fun createBook(keyword: String, title: String): Single<String> = books()
            .map {
                val id = generateId(it.keys)
                val file = File(directory(), "$id.nmp")
                val book: Book = BookImpl(file).apply { createBook(id, keyword, title) }
                books?.plusAssign((id to book))
                id
            }
            .subscribeOn(schedulers.io())

    override fun importBook(uri: Uri): Single<String> = books()
            .map {
                val id = generateId(it.keys)
                val file = File(directory(), "$id.nmp")
                val input = contentResolver.openInputStream(uri)
                        ?: throw IOException("unable to read uri")
                val output = FileOutputStream(file)
                input.copyTo(output)
                input.safeClose()
                output.safeClose()
                val book: Book = BookImpl(file).apply { openBook() }
                books?.entries?.filter { entry ->
                    entry.value.getUniqueId() == book.getUniqueId()
                }?.forEach { entry -> deleteBookImplicit(entry.key) }
                books?.plusAssign((id to book))
                id
            }
            .subscribeOn(schedulers.io())

    override fun listBooks(): Single<Map<String, Book>> = books()
            .subscribeOn(schedulers.io())

    override fun activeBook(): Single<Book> = activeBookId()
            .flatMap { books() }
            .map { it[activeId] ?: throw NoActiveBookException() }
            .subscribeOn(schedulers.io())

    override fun switchBook(id: String): Completable = saveActiveBookId(id)
            .doOnComplete { activeId = id }
            .subscribeOn(schedulers.io())

    override fun deleteBook(id: String): Completable = books()
            .map { deleteBookImplicit(id) }
            .ignoreElement()
            .subscribeOn(schedulers.io())

    private fun books(): Single<Map<String, Book>> {
        return books?.let { Single.just(Collections.unmodifiableMap(it)) }
                ?: Single.create { emitter ->
                    val books = directory()
                            .listFiles()
                            .filter { it.name != CONTENTS_FILE }
                            .associate { it.name to BookImpl(it).apply { openBook() } as Book }
                            .toMutableMap()
                    this.books = books
                    emitter.onSuccess(books)
                }
    }

    private fun deleteBookImplicit(id: String) {
        books?.remove(id)?.run {
            lock()
            getFile().delete()
        }
    }

    private fun activeBookId(): Single<String> {
        return activeId?.let { Single.just(it) } ?: Single.create { emitter ->
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
            id = sha1(ids.joinToString() + System.currentTimeMillis())
        } while (ids.contains(id))
        return id
    }

    private fun saveActiveBookId(id: String): Completable {
        return Completable.create { emitter ->
            writeActiveBookId(id).takeIf { true }
                    ?.run { emitter.onComplete() }
                    ?: emitter.onError(Exception("Failed to assign active book"))
        }.doOnComplete { activeId = id }
    }

    private fun readActiveBookId(): String? {
        val shelf = File(directory(), CONTENTS_FILE)
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
        val shelf = File(directory(), CONTENTS_FILE)
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

    private fun directory(): File {
        return dir.takeIf { it.exists() } ?: dir.apply { mkdirs() }
    }

}

private const val CONTENTS_FILE = "shelf.dat"