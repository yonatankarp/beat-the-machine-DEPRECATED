package com.yonatankarp.ai.guess.game

class Guess(var words: List<String>? = null)

data class Response(val state: List<Pair<String, String>> = emptyList(), val guessedWords: List<String> = emptyList())

enum class GuessResult {
    HIT,
    MISSED
}
