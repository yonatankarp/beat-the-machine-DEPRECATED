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
    fun `should map guess to results`(id: Int, guess: Guess, expected: List<Pair<String, GuessResult>>) {
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
                    "---" to GuessResult.MISS,
                    "------" to GuessResult.MISS,
                    "--" to GuessResult.MISS,
                    "-" to GuessResult.MISS,
                    "---" to GuessResult.MISS,
                ),
            ),
            Arguments.of(
                0,
                Guess(listOf("man")),
                listOf(
                    "man" to GuessResult.HIT,
                    "------" to GuessResult.MISS,
                    "--" to GuessResult.MISS,
                    "-" to GuessResult.MISS,
                    "man" to GuessResult.HIT,
                ),
            ),
            Arguments.of(
                0,
                Guess("man stands on a man".split(" ")),
                listOf(
                    "man" to GuessResult.HIT,
                    "stands" to GuessResult.HIT,
                    "on" to GuessResult.HIT,
                    "a" to GuessResult.HIT,
                    "man" to GuessResult.HIT,
                ),
            ),
            Arguments.of(
                0,
                Guess(null),
                listOf(
                    "---" to GuessResult.MISS,
                    "------" to GuessResult.MISS,
                    "--" to GuessResult.MISS,
                    "-" to GuessResult.MISS,
                    "---" to GuessResult.MISS,
                ),
            ),
        )
    }
}
