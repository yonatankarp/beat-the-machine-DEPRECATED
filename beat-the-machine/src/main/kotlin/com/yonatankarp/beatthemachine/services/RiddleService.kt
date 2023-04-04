package com.yonatankarp.beatthemachine.services

import com.yonatankarp.beatthemachine.models.Guess
import com.yonatankarp.beatthemachine.models.Guess.GuessResult
import com.yonatankarp.beatthemachine.models.Guess.GuessResult.HIT
import com.yonatankarp.beatthemachine.models.Guess.GuessResult.MISS
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class RiddleService {

    companion object {
        private const val MASK_CHARACTER = "-"

        private val log = LoggerFactory.getLogger(RiddleService::class.java)
        private val WHITESPACE_REGEX = """\s+""".toRegex()
    }

    fun handleGuess(id: Int, guess: Guess): List<Pair<String, GuessResult>> {
        val riddle = getRiddle(id)
        if (guess.words == null) return riddle.initPrompt()

        return maskNoneGuessedWords(guess.words, riddle.prompt)
            .also { log.info("Phrase '${riddle.prompt}' with guess $guess have the results: $it") }
    }

    fun maskNoneGuessedWords(words: List<String>?, prompt: String): List<Pair<String, GuessResult>> =
        prompt.lowercase().split(WHITESPACE_REGEX)
            .map { word ->
                if (words?.contains(word) == true) {
                    word to HIT
                } else {
                    MASK_CHARACTER.repeat(word.length) to MISS
                }
            }

    fun getRiddle(id: Int) = RiddleManager.riddles[id]

    fun getRandomRiddle() =
        getRiddle(Random.nextInt(from = 0, until = RiddleManager.numberOfRiddles))
            .also { log.info("Reading riddle #$it") }
}
