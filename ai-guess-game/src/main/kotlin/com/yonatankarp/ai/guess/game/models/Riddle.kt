package com.yonatankarp.ai.guess.game.models

import com.yonatankarp.ai.guess.game.models.GuessResult.HIT
import com.yonatankarp.ai.guess.game.models.GuessResult.MISS
import com.yonatankarp.ai.guess.game.utils.toHiddenString

data class Riddle(val id: Int, val startPrompt: String, val prompt: String, val url: String) {
    fun giveUp() = Response(
        this.prompt
            .split(" ")
            .map { it to HIT.name }
    )

    fun initPrompt() = Response(
        this.prompt
            .split(" ")
            .map { it.toHiddenString() to MISS.name }
            .toList()
    )
}
