package com.yonatankarp.ai.guess.game.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class FavIconController {
    @GetMapping("favicon.ico")
    @ResponseBody
    fun favicon() {
    }
}
