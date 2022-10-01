package com.yonatankarp.ai.guess.game

class Guess(var phrase: String? = null)

data class Response(val state: Map<String, String>)

enum class GuessResult {
    CORRECT,
    WRONG_LOCATION,
    MISSED
}
