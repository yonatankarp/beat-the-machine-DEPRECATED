package com.yonatankarp.ai.guess.game.services

import com.yonatankarp.ai.guess.game.dto.RiddleDto
import com.yonatankarp.ai.guess.game.models.Guess
import com.yonatankarp.ai.guess.game.models.Guess.GuessResult
import com.yonatankarp.ai.guess.game.repository.RiddleRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class RiddleServiceTest {

    private val repository = mockk<RiddleRepository>()
    private val service: RiddleService = RiddleService(repository)

    @ParameterizedTest
    @MethodSource("provideTestData")
    fun `should map guess to results`(id: Int, guess: Guess, expected: List<Pair<String, String>>) {
        // Given
        val riddle = buildRiddle()
        every { repository.findById(any()) } returns riddle

        // When
        val actual = service.handleGuess(id, guess)

        // Then
        assertEquals(expected, actual)
    }

    private fun buildRiddle() =
        RiddleDto(
            id = 12345,
            startPrompt = "--- ------ -- - ---",
            prompt = "man stands on a man",
            url = "some nice url"
        )

    companion object {
        @JvmStatic
        fun provideTestData(): Stream<Arguments> = Stream.of(
            Arguments.of(
                0,
                Guess(listOf("incorrect guess")),
                listOf(
                    "---" to GuessResult.MISS.name,
                    "------" to GuessResult.MISS.name,
                    "--" to GuessResult.MISS.name,
                    "-" to GuessResult.MISS.name,
                    "---" to GuessResult.MISS.name
                )
            ),
            Arguments.of(
                0,
                Guess(listOf("man")),
                listOf(
                    "man" to GuessResult.HIT.name,
                    "------" to GuessResult.MISS.name,
                    "--" to GuessResult.MISS.name,
                    "a" to GuessResult.HIT.name, // TODO: fix after fixing word recognition
                    "man" to GuessResult.HIT.name
                )
            ),
            Arguments.of(
                0,
                Guess(listOf("man stands on a man")),
                listOf(
                    "man" to GuessResult.HIT.name,
                    "stands" to GuessResult.HIT.name,
                    "on" to GuessResult.HIT.name,
                    "a" to GuessResult.HIT.name,
                    "man" to GuessResult.HIT.name
                )
            ),
            Arguments.of(
                0,
                Guess(),
                listOf(
                    "---" to GuessResult.MISS.name,
                    "------" to GuessResult.MISS.name,
                    "--" to GuessResult.MISS.name,
                    "-" to GuessResult.MISS.name,
                    "---" to GuessResult.MISS.name
                )
            )
        )
    }
}
