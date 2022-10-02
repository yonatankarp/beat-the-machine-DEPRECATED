package com.yonatankarp.ai.guess.game

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import kotlin.random.Random

@Controller
class RiddleController(
    val riddleService: RiddleService,
    val riddleManager: RiddleManager
) {
    companion object {
        private val log = LoggerFactory.getLogger(RiddleController::class.java)
    }

    @RequestMapping(value = ["/", "index", "index.html"])
    fun index(model: Model): String {
        val riddleIndex = Random.nextInt(from = 0, until = riddleManager.numberOfRiddles)
        log.info("Reading riddle id: $riddleIndex")
        riddleManager.getRiddle(riddleIndex).let {
            model.addAttribute("guess", Guess(listOf("Enter a word...")))
            model.addAttribute("riddle", it)
            model.addAttribute("response", it.initPrompt())
        }
        return "index"
    }

    @RequestMapping(value = ["/{id}/i-give-up"])
    fun iGiveUp(@PathVariable id: Int, model: Model): String {
        log.info("Giving up on riddle id: $id")
        riddleManager.getRiddle(id.toRiddleId()).let {
            model.addAttribute("guess", Guess(listOf()))
            model.addAttribute("riddle", it)
            model.addAttribute("response", it.giveUp())
        }

        return "game"
    }

    @GetMapping(value = ["/{id}"], produces = [MediaType.IMAGE_PNG_VALUE])
    @ResponseBody
    fun getImage(@PathVariable id: Int) = riddleService.getImage(id.toRiddleId())

    @PostMapping("/{id}/guess")
    fun submitGuess(@PathVariable id: Int, @ModelAttribute guess: Guess, model: Model): String {
        val riddleId = id.toRiddleId()
        log.info("Guess for id $riddleId is ${guess.words}")
        riddleService.handleGuess(riddleId, guess)
            .let {
                model.addAttribute("riddle", riddleManager.getRiddle(riddleId))
                model.addAttribute("guess", guess)
                model.addAttribute("response", it)
            }
        return "game"
    }

    private fun Int.toRiddleId() = this % (riddleManager.numberOfRiddles)

    @GetMapping("favicon.ico")
    @ResponseBody
    fun favicon() {
    }
}
