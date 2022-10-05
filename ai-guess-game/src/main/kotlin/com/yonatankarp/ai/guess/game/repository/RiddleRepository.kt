package com.yonatankarp.ai.guess.game.repository

import com.yonatankarp.ai.guess.game.dto.RiddleDto
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.ResultSet

@Repository
class RiddleRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) {

    @Transactional
    fun save(riddle: RiddleDto): Boolean {
        val statement = """
            INSERT INTO RIDDLES (ID, START_PROMPT, PROMPT, URL)
            VALUES (:id, :start_prompt, :prompt, :url);
        """.trimMargin()
        val namedParams = MapSqlParameterSource()
        namedParams.addValue("id", riddle.id)
        namedParams.addValue("start_prompt", riddle.startPrompt)
        namedParams.addValue("prompt", riddle.prompt)
        namedParams.addValue("url", riddle.url)
        return jdbcTemplate.update(statement, namedParams) == 1
    }

    fun findById(id: Int): RiddleDto? {
        val statement = """SELECT * FROM RIDDLES WHERE ID = :id;"""
        val namedParams = MapSqlParameterSource()
        namedParams.addValue("id", id)
        return jdbcTemplate.queryForObject(statement, namedParams, RiddleDtoMapper())
    }

    fun getRandomRiddle(): RiddleDto? {
        val statement = """
            SELECT * FROM RIDDLES
            ORDER BY RAND()
            LIMIT 1;
        """.trimIndent()
        return jdbcTemplate.queryForObject(statement, MapSqlParameterSource(), RiddleDtoMapper())
    }

    fun countRiddles(): Int {
        val statement = """SELECT count(1) from RIDDLES;"""
        return jdbcTemplate.queryForObject(statement, MapSqlParameterSource(), Int::class.java)
    }

    inner class RiddleDtoMapper : RowMapper<RiddleDto> {
        override fun mapRow(rs: ResultSet, rowNum: Int): RiddleDto =
            RiddleDto()
                .apply {
                    id = rs.getInt("ID")
                    startPrompt = rs.getString("START_PROMPT")
                    prompt = rs.getString("PROMPT")
                    url = rs.getString("URL")
                }
    }
}
