package com.yonatankarp.ai.guess.game.models

data class Guess(var words: List<String>? = null) {
    enum class GuessResult {
        HIT,
        MISS
    }
}
