package com.yonatankarp.ai.guess.game

import org.springframework.stereotype.Component

@Component
class RiddleManager {

    private val riddles: Array<Riddle> = arrayOf(
        Riddle(
            id = 0,
            startPrompt = "man stands on a man".toHiddenString(),
            prompt = "man stands on a man",
            image = loadImage(0)
        ),
        Riddle(
            id = 1,
            startPrompt = "dolphin on fire".toHiddenString(),
            prompt = "dolphin on fire",
            image = loadImage(1)
        ),
        Riddle(
            id = 2,
            startPrompt = "astronaut eating the moon".toHiddenString(),
            prompt = "astronaut eating the moon",
            image = loadImage(2)
        ),
        Riddle(
            id = 3,
            startPrompt = "the quiet before the storm".toHiddenString(),
            prompt = "the quiet before the storm",
            image = loadImage(3)
        )
    )

    val numberOfRiddles: Int
        get() = riddles.size

    fun getImage(id: Int) = riddles[id].image

    fun getRiddle(id: Int) = riddles[id]

    private fun loadImage(id: Int) =
        javaClass.getResourceAsStream("/static/images/$id.png")!!.readBytes()
}

data class Riddle(val id: Int, val startPrompt: String, val prompt: String, val image: ByteArray) {
    fun giveUp() = Response(
        this.prompt
            .split(" ")
            .map { it to GuessResult.HIT.name }
    )

    fun initPrompt() = Response(
        this.prompt
            .split(" ")
            .map { it.toHiddenString() to GuessResult.MISS.name }
            .toList()
    )
}

fun String.toHiddenString(): String {
    val builder = StringBuilder()
    for(c in this) {
        builder.append(if(c != ' ') "-" else " ")
    }
    return builder.toString()
}
