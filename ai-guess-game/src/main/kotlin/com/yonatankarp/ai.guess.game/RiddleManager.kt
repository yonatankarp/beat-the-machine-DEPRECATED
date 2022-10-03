package com.yonatankarp.ai.guess.game

import org.springframework.stereotype.Component

@Component
class RiddleManager {

    private val riddles: Array<Riddle> = arrayOf(
        Riddle(
            id = 0,
            startPrompt = "man stands on a man".toHiddenString(),
            prompt = "man stands on a man",
            url = "https://s3.amazonaws.com/ai.protogenes/art/28b9da08-4282-11ed-8be2-ee31c059bf00.png"
        ),
        Riddle(
            id = 1,
            startPrompt = "dolphin on fire".toHiddenString(),
            prompt = "dolphin on fire",
            url = "https://s3.amazonaws.com/ai.protogenes/art/840e15ae-18bb-11ed-9f15-ba15d03b6eca.png"
        ),
        Riddle(
            id = 2,
            startPrompt = "astronaut eating the moon".toHiddenString(),
            prompt = "astronaut eating the moon",
            url = "https://s3.amazonaws.com/ai.protogenes/art/9d73d604-4189-11ed-8cdd-2e988650e75d.png"
        ),
        Riddle(
            id = 3,
            startPrompt = "the quiet before the storm".toHiddenString(),
            prompt = "the quiet before the storm",
            url = "https://s3.amazonaws.com/ai.protogenes/art/13bb8906-418a-11ed-8cdd-2e988650e75d.png"
        ),
        Riddle(
            id = 4,
            startPrompt = "dragon eating a cookie".toHiddenString(),
            prompt = "dragon eating a cookie",
            url = "https://s3.amazonaws.com/ai.protogenes/art/847263c4-42af-11ed-b2cd-366e053c1cb8.png"
        )
    )

    val numberOfRiddles: Int
        get() = riddles.size

    fun getRiddle(id: Int) = riddles[id]
}

data class Riddle(val id: Int, val startPrompt: String, val prompt: String, val url: String) {
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
    for (c in this) {
        builder.append(if (c != ' ') "-" else " ")
    }
    return builder.toString()
}
