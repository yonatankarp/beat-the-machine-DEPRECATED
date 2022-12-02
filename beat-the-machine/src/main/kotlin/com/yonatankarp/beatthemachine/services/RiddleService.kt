package com.yonatankarp.beatthemachine.services

import com.yonatankarp.beatthemachine.models.Guess
import com.yonatankarp.beatthemachine.models.Guess.GuessResult.HIT
import com.yonatankarp.beatthemachine.models.Guess.GuessResult.MISS
import com.yonatankarp.beatthemachine.utils.toHiddenString
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class RiddleService {

    companion object {
        private val log = LoggerFactory.getLogger(RiddleService::class.java)
    }

    fun handleGuess(id: Int, guess: Guess): List<Pair<String, String>> {
        val riddle = getRiddle(id)
        if (guess.words == null) return riddle.initPrompt()

        val riddlePhrase = riddle.prompt.split(" ")
        val guessPhrase = guess.words!!.joinToString(separator = " ")

        val results = mutableListOf<Pair<String, String>>()

        riddlePhrase.forEach { word ->
            results += if (guessPhrase.contains(word, ignoreCase = true)) {
                word to HIT.name
            } else {
                word.toHiddenString() to MISS.name
            }
        }

        log.info("Phrase '$riddlePhrase' with guess $guess have the results: $results")

        return results
    }

    fun getRiddle(id: Int) = RiddleManager.riddles[id]

    fun getRandomRiddle() =
        getRiddle(Random.nextInt(from = 0, until = RiddleManager.numberOfRiddles))
            .also { log.info("Reading riddle #$it") }
}
