package com.yonatankarp.beatthemachine.models

import com.yonatankarp.beatthemachine.models.Guess.GuessResult.MISS
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RiddleTest {

    @Test
    fun `should mark all prompt as hit when player give up`() {
        // Given
        val riddle = Riddle(id = 10, startPrompt = "--- --- ---", prompt = "dog eat god", url = "a nice url")

        // When
        val actual = riddle.giveUp()

        // Then
        riddle.prompt.split(" ").forEachIndexed { i, word ->
            assertEquals(word, actual[i].first)
            assertEquals(MISS.name, actual[i].second)
        }
    }

    @Test
    fun `should init prompt with missed guesses`() {
        // Given
        val riddle = Riddle(id = 10, startPrompt = "--- --- ---", prompt = "dog eat god", url = "a nice url")

        // When
        val actual = riddle.initPrompt()

        // Then
        actual.forEach {
            assertEquals("---", it.first)
            assertEquals(MISS.name, it.second)
        }
    }
}
