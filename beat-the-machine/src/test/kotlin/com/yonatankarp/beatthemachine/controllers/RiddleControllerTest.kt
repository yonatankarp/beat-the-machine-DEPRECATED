package com.yonatankarp.ai.guess.game.controllers

import com.ninjasquad.springmockk.MockkBean
import com.yonatankarp.ai.guess.game.models.Guess
import com.yonatankarp.ai.guess.game.models.Guess.GuessResult.MISS
import com.yonatankarp.ai.guess.game.models.Riddle
import com.yonatankarp.ai.guess.game.services.RiddleService
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@ExtendWith(SpringExtension::class)
@WebMvcTest(controllers = [RiddleController::class])
class RiddleControllerTest {

    @MockkBean
    private lateinit var service: RiddleService

    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @ParameterizedTest
    @ValueSource(strings = ["/", "/index", "/index.html"])
    fun `should return index page`(endpoint: String) {
        val riddle = buildRiddle()
        every { service.getRandomRiddle() } returns riddle

        mockMvc.get(endpoint)
            .andExpect {
                status { isOk() }
                view { name("index") }
                model {
                    attribute("guess", Guess())
                    attribute("riddle", riddle)
                    attribute("response", buildHiddenStringList())
                }
            }

        verify(exactly = 1) { service.getRandomRiddle() }
    }

    @Test
    fun `should return current riddle with full prompt solved`() {
        val riddle = buildRiddle()
        every { service.getRiddle(any()) } returns riddle
        every { service.getNumberOfRiddles() } returns Int.MAX_VALUE
        every { service.getRiddle(any()) } returns riddle

        mockMvc.post("/${riddle.id}/i-give-up")
            .andExpect {
                status { isOk() }
                view { name("game") }
                model {
                    attribute("guess", Guess(emptyList()))
                    attribute("riddle", riddle)
                    attribute("response", buildMissList())
                }
            }

        verify(exactly = 1) { service.getRiddle(riddle.id) }
        verify(exactly = 1) { service.getNumberOfRiddles() }
        verify(exactly = 1) { service.getRiddle(eq(riddle.id)) }
    }

    @Test
    fun `should submit guess`() {
        val riddle = buildRiddle()
        val guess = Guess()
        val missedGuesses = buildMissList()
        every { service.handleGuess(any(), any()) } returns missedGuesses
        every { service.getNumberOfRiddles() } returns Int.MAX_VALUE
        every { service.getRiddle(any()) } returns riddle

        mockMvc.post("/${riddle.id}/guess") {
            sessionAttrs = mapOf("guess" to guess)
        }.andExpect {
            status { isOk() }
            view { name("game") }
            model {
                attribute("guess", guess)
                attribute("riddle", riddle)
                attribute("response", missedGuesses)
            }
        }

        verify(exactly = 1) { service.handleGuess(eq(riddle.id), eq(guess)) }
        verify(exactly = 1) { service.getNumberOfRiddles() }
        verify(exactly = 1) { service.getRiddle(eq(riddle.id)) }
    }

    private fun buildRiddle() =
        Riddle(
            id = 1234567,
            startPrompt = "- -- - ----",
            prompt = "i am a test",
            url = "some nice url"
        )

    private fun buildMissList() =
        listOf(
            "i" to MISS.name,
            "am" to MISS.name,
            "a" to MISS.name,
            "test" to MISS.name
        )

    private fun buildHiddenStringList() =
        listOf(
            "-" to MISS.name,
            "--" to MISS.name,
            "-" to MISS.name,
            "----" to MISS.name
        )
}
