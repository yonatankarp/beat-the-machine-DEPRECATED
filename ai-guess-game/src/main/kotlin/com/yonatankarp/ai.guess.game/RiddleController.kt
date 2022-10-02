package com.yonatankarp.ai.guess.game

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RiddleController(private val service: RiddleService) {
    @GetMapping(value = ["/{id}"], produces = [MediaType.IMAGE_PNG_VALUE])
    @ResponseBody
    fun getImage(@PathVariable id: Int) = service.getImage(id)

    @PostMapping(value = ["/{id}/guess"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun guess(@PathVariable id: Int, @RequestBody guess: Guess) = service.handleGuess(id, guess)
}
