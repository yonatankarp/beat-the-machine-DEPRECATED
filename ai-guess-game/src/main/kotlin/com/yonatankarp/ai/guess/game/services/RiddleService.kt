package com.yonatankarp.ai.guess.game.services

import com.yonatankarp.ai.guess.game.models.Guess
import com.yonatankarp.ai.guess.game.models.GuessResult.HIT
import com.yonatankarp.ai.guess.game.models.GuessResult.MISS
import com.yonatankarp.ai.guess.game.models.Response
import com.yonatankarp.ai.guess.game.utils.toHiddenString
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RiddleService {

    companion object {
        private val log = LoggerFactory.getLogger(RiddleService::class.java)
    }

    fun handleGuess(id: Int, guess: Guess): Response {
        val riddle = RiddleManager.riddles[id]
        if (guess.words == null) return riddle.initPrompt()

        val riddlePhrase = riddle.prompt.split(" ")
        val guessPhrase = guess.words!!.joinToString(separator = " ")

        val result = mutableListOf<Pair<String, String>>()

        riddlePhrase.forEach { word ->
            result += if (guessPhrase.contains(word, ignoreCase = true)) {
                word to HIT.name
            } else {
                word.toHiddenString() to MISS.name
            }
        }

        log.info("Phrase '$riddlePhrase' with guess $guess have the results: $result")

        return Response(result, guess.words!!)
    }
}
