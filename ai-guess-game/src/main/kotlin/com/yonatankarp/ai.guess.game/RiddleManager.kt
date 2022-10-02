package com.yonatankarp.ai.guess.game

import org.springframework.stereotype.Component

@Component
class RiddleManager {

    private val riddles: Map<Int, Riddle> = mapOf(
        1 to Riddle(1, "dolphin on fire", loadImage(1)),
        2 to Riddle(2, "astronaut eating the moon", loadImage(2)),
        3 to Riddle(3, "the quiet before the storm", loadImage(3))
    )

    fun getImage(id: Int) = riddles[id]!!.image

    fun getRiddle(id: Int) = riddles[id]!!

    private fun loadImage(id: Int) =
        javaClass.getResourceAsStream("/static/images/$id.png")!!.readBytes()
}

data class Riddle(val id: Int, val phrase: String, val image: ByteArray)
