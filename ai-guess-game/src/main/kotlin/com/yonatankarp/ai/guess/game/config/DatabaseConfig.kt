package com.yonatankarp.ai.guess.game.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(value = ["com.yonatankarp.ai.guess.game.repository"])
class DatabaseConfig
