package com.yonatankarp.ai.guess.game

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RiddleService(val riddleManager: RiddleManager) {

    companion object {
        private val log = LoggerFactory.getLogger(RiddleService::class.java)
    }

    fun getImage(id: Int) = riddleManager.getImage(id)

    fun handleGuess(id: Int, guess: Guess): Response {
        if (guess.words == null) return Response()
        val guessPhrase = guess.words!!.joinToString(separator = " ")

        val riddlePhrase = riddleManager.getRiddle(id)?.phrase?.split(" ")
        require(riddlePhrase != null) { "Given id doesn't exist in the system" }

        val result = mutableListOf<Pair<String, String>>()

        riddlePhrase.forEach { word ->
            result += if (guessPhrase.contains(word, ignoreCase = true)) {
                word to GuessResult.HIT.name
            } else {
                word.toHiddenString() to GuessResult.MISSED.name
            }
        }

        log.info("Phrase '$riddlePhrase' with guess $guess have the results: $result")

        return Response(result, guess.words!!)
    }

    private fun String.toHiddenString(): String {
        val builder = StringBuilder()
        for (i in 0..(this.length)) builder.append("-")
        return builder.toString()
    }

    fun iGiveUp(id: Int) = Response(
        riddleManager.getRiddle(id)?.phrase
            ?.split(" ")
            ?.map { it to GuessResult.HIT.name } ?: emptyList()
    )
}
