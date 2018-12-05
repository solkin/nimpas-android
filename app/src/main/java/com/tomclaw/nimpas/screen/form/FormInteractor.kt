package com.tomclaw.nimpas.screen.form

import com.tomclaw.nimpas.journal.Card
import com.tomclaw.nimpas.journal.Group
import com.tomclaw.nimpas.journal.Journal
import com.tomclaw.nimpas.journal.Note
import com.tomclaw.nimpas.journal.Password
import com.tomclaw.nimpas.journal.TYPE_CARD
import com.tomclaw.nimpas.journal.TYPE_GROUP
import com.tomclaw.nimpas.journal.TYPE_NOTE
import com.tomclaw.nimpas.journal.TYPE_PASSWORD
import com.tomclaw.nimpas.screen.form.model.Widget
import com.tomclaw.nimpas.util.SchedulersFactory
import io.reactivex.Observable
import java.util.Random

interface FormInteractor {

    fun getWidgets(): Observable<List<Widget>>

}

class FormInteractorImpl(
        private val recordType: Int,
        private val groupId: Long,
        private val journal: Journal,
        private val schedulers: SchedulersFactory
) : FormInteractor {

    private val random = Random(System.currentTimeMillis())


    override fun getWidgets(): Observable<List<Widget>> {
        val widgets: List<Widget> = listOf(
                Widget.Label(1L, "Label"),
                Widget.Input(2L, "Hint", "Text")
        )
        return Observable.just(widgets)
                .subscribeOn(schedulers.io())
    }

    init {
        val id = journal.nextId()
        val time = System.currentTimeMillis()
        val record = when (recordType) {
            TYPE_GROUP -> Group(
                    id,
                    groupId,
                    time,
                    title = generateRandomPhrase(random.nextInt(3) + 2)
            )
            TYPE_PASSWORD -> Password(
                    id,
                    groupId,
                    time,
                    title = generateRandomPhrase(random.nextInt(3) + 2),
                    username = randomString(),
                    password = randomString(),
                    url = "https://" + randomString(10) + "." + randomString(3),
                    description = randomString().capitalize()
            )
            TYPE_CARD -> Card(
                    id,
                    groupId,
                    time,
                    title = generateRandomPhrase(random.nextInt(3) + 2),
                    number = (random.nextInt(8999) + 1000).toString() + " " +
                            (random.nextInt(8999) + 1000).toString() + " " +
                            (random.nextInt(8999) + 1000).toString() + " " +
                            (random.nextInt(8999) + 1000).toString(),
                    expiration = random.nextInt(),
                    holder = randomString(),
                    security = random.nextInt(126) + 100
            )
            TYPE_NOTE -> Note(
                    id,
                    groupId,
                    time,
                    title = generateRandomPhrase(random.nextInt(3) + 2),
                    text = generateRandomText()
            )
            else -> throw IllegalArgumentException()
        }
        journal.addRecord(record).subscribe()
    }

    fun generateRandomText(): String {
        val wordCount = 10 + random.nextInt(13)
        return generateRandomText(wordCount)
    }

    fun generateRandomText(wordCount: Int): String {
        val sb = StringBuilder(wordCount)
        for (i in 0 until wordCount) {
            sb.append(generateRandomWord(i == 0)).append(if (i < wordCount - 1) " " else ".")
        }
        return sb.toString()
    }

    fun generateRandomPhrase(wordCount: Int): String {
        val sb = StringBuilder(wordCount)
        for (i in 0 until wordCount) {
            sb.append(generateRandomWord(i == 0)).append(if (i < wordCount - 1) " " else "")
        }
        return sb.toString()
    }

    fun generateRandomWord(): String {
        return generateRandomWord(true)
    }

    fun generateRandomWord(capitalize: Boolean): String {
        val wordLength = 4 + random.nextInt(6)
        // Initialize a Random Number Generator with SysTime as the seed
        val sb = StringBuilder(wordLength)
        for (i in 0 until wordLength) { // For each letter in the word
            sb.append('a' + random.nextInt('z' - 'a')) // Add it to the String
        }
        val word = sb.toString()
        return if (capitalize) {
            word[0].toString().toUpperCase() + word.substring(1)
        } else {
            word
        }
    }

    fun randomString(): String {
        return randomString(16)
    }

    fun randomString(length: Int): String {
        return randomString(random, length, length)
    }

    fun randomString(r: Random, minChars: Int, maxChars: Int): String {
        var wordLength = minChars
        val delta = maxChars - minChars
        if (delta > 0) {
            wordLength += r.nextInt(delta)
        }
        val sb = StringBuilder(wordLength)
        for (i in 0 until wordLength) {
            sb.append('a' + r.nextInt('z' - 'a'))
        }
        return sb.toString()
    }

}