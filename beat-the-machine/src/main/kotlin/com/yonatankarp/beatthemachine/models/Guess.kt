package com.yonatankarp.beatthemachine.models

data class Guess(var words: List<String>? = null) {
    enum class GuessResult {
        HIT,
        MISS
    }
}
