package com.yonatankarp.beatthemachine.services

import com.yonatankarp.beatthemachine.models.Guess
import com.yonatankarp.beatthemachine.models.Guess.GuessResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class RiddleServiceTest {

    private val service: RiddleService = RiddleService()

    @ParameterizedTest
    @MethodSource("provideTestData")
    fun `should map guess to results`(id: Int, guess: Guess, expected: List<Pair<String, String>>) {
        val actual = service.handleGuess(id, guess)
        assertEquals(expected, actual)
    }

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
                Guess(null),
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
