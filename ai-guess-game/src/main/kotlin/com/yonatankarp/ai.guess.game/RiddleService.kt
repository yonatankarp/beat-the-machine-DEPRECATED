package com.yonatankarp.ai.guess.game

import com.yonatankarp.ai.guess.game.GuessResult.CORRECT
import com.yonatankarp.ai.guess.game.GuessResult.MISSED
import com.yonatankarp.ai.guess.game.GuessResult.WRONG_LOCATION
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RiddleService(val riddleManager: RiddleManager) {

    companion object {
        private val log = LoggerFactory.getLogger(RiddleService::class.java)
    }

    fun getImage(id: Int) = riddleManager.getImage(id)

    fun handleGuess(id: Int, guess: Guess): Response {
        val phrase = riddleManager.getRiddle(id)?.phrase
        require(phrase != null) { "Given id doesn't exist in the system" }

        val phraseInIndex = phrase.split(" ")
        val result = mutableMapOf<String, String>()

        guess.phrase?.split(" ")?.forEachIndexed { index, word ->
            if (phrase.contains(word.lowercase())) {
                result[word] =
                    if (phraseInIndex[index] == word.lowercase()) CORRECT.name.lowercase()
                    else WRONG_LOCATION.name.lowercase()
            } else {
                result[word] = MISSED.name.lowercase()
            }
        }

        log.info("Phrase '$phrase' with guess $guess have the results: $result")

        return Response(result)
    }
}
