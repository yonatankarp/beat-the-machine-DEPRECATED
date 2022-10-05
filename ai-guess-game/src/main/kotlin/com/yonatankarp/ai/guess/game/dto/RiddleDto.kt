package com.yonatankarp.ai.guess.game.dto

import com.yonatankarp.ai.guess.game.models.Riddle
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "RIDDLES")
data class RiddleDto(
    @Id
    var id: Int? = null,

    @Column(nullable = false)
    var startPrompt: String?,

    @Column(nullable = false)
    var prompt: String?,

    @Column(nullable = false)
    var url: String?
) {
    constructor() : this(null, null, null, null)

    companion object {
        fun RiddleDto.fromDto() =
            Riddle(
                id = id ?: -1,
                startPrompt = startPrompt ?: "",
                prompt = prompt ?: "",
                url = url ?: ""
            )
    }
}
