package com.app.backend.database.services

import com.app.backend.database.entities.Rankings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object RankingService {
    suspend fun addRanking(
        boardId: Int,
        timeMillis: Long,
    ) = withContext(Dispatchers.IO) {
        transaction {
            Rankings.insert {
                it[this.boardId] = boardId
                it[this.timeMillis] = timeMillis
            }
        }
    }

    fun getRankingsForBoard(
        boardId: Int,
        topN: Int,
    ): List<Long> {
        return transaction {
            Rankings
                .selectAll().where { Rankings.boardId eq boardId }
                .orderBy(Rankings.timeMillis)
                .take(topN)
                .map { it[Rankings.timeMillis] }
        }
    }
}
