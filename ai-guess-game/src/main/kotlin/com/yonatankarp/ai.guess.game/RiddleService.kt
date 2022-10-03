package com.yonatankarp.ai.guess.game

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RiddleService(val riddleManager: RiddleManager) {

    companion object {
        private val log = LoggerFactory.getLogger(RiddleService::class.java)
    }

    fun handleGuess(id: Int, guess: Guess): Response {
        val riddle = riddleManager.getRiddle(id)
        if (guess.words == null) return riddle.initPrompt()

        val riddlePhrase = riddle.prompt.split(" ")
        val guessPhrase = guess.words!!.joinToString(separator = " ")

        val result = mutableListOf<Pair<String, String>>()

        riddlePhrase.forEach { word ->
            result += if (guessPhrase.contains(word, ignoreCase = true)) {
                word to GuessResult.HIT.name
            } else {
                word.toHiddenString() to GuessResult.MISS.name
            }
        }

        log.info("Phrase '$riddlePhrase' with guess $guess have the results: $result")

        return Response(result, guess.words!!)
    }
}
