package com.yonatankarp.ai.guess.game.models

data class Guess(var words: List<String> = emptyList()) {
    enum class GuessResult {
        HIT,
        MISS
    }
}
