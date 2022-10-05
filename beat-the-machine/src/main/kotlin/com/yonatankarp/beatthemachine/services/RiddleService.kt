package com.yonatankarp.ai.guess.game.services

import com.yonatankarp.ai.guess.game.dto.RiddleDto.Companion.fromDto
import com.yonatankarp.ai.guess.game.models.Guess
import com.yonatankarp.ai.guess.game.models.Guess.GuessResult.HIT
import com.yonatankarp.ai.guess.game.models.Guess.GuessResult.MISS
import com.yonatankarp.ai.guess.game.models.Riddle
import com.yonatankarp.ai.guess.game.repository.RiddleRepository
import com.yonatankarp.ai.guess.game.utils.toHiddenString
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RiddleService(
    private val repository: RiddleRepository
) {

    companion object {
        private val log = LoggerFactory.getLogger(RiddleService::class.java)
    }

    fun handleGuess(id: Int, guess: Guess): List<Pair<String, String>> {
        val riddle = getRiddle(id)
        if (guess.words.isEmpty()) return riddle.initPrompt()

        val riddlePhrase = riddle.prompt.split(" ")
        val guessPhrase = guess.words.joinToString(separator = " ")

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

    fun getRiddle(id: Int): Riddle =
        repository.findById(id)
            ?.fromDto()
            ?: throw RuntimeException("Riddle id $id doesn't exist")

    fun getRandomRiddle() =
        repository.getRandomRiddle()
            ?.fromDto()
            .also { log.info("Reading riddle #$it") }
            ?: throw RuntimeException("No riddle could be found in the database...")

    fun getNumberOfRiddles(): Int = repository.countRiddles()
}
