package com.yonatankarp.ai.guess.game.models

import com.yonatankarp.ai.guess.game.models.Guess.GuessResult.MISS

data class Riddle(val id: Int, val startPrompt: String, val prompt: String, val url: String) {
    fun giveUp() =
        this.prompt
            .split(" ")
            .map { it to MISS.name }

    fun initPrompt() =
        this.startPrompt
            .split(" ")
            .map { it to MISS.name }
            .toList()
}
