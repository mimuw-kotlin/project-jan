package com.app.backend.database.services

import com.app.backend.database.entities.SudokuBoards
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

// Basic functions that provide comunication with databse
object SudokuService {
    suspend fun insertSudoku(board: String, id: Int) = withContext(Dispatchers.IO) {
        transaction {
            SudokuBoards.insert {
                it[SudokuBoards.board] = board
                it[SudokuBoards.id] = id
            }
        }
    }

    suspend fun getSudokuById(id: Int): String? = withContext(Dispatchers.IO) {
        transaction {
            SudokuBoards.select { SudokuBoards.id eq id }
                .map { it[SudokuBoards.board] }
                .singleOrNull()
        }
    }

    suspend fun getAllSudokus(): List<String> = withContext(Dispatchers.IO) {
        transaction {
            SudokuBoards.selectAll()
                .map { it[SudokuBoards.board] }
        }
    }

    suspend fun updateSudoku(board: String, id: Int) = withContext(Dispatchers.IO) {
        transaction {
            SudokuBoards.update({ SudokuBoards.id eq id }) {
                it[SudokuBoards.board] = board
            }
        }
    }
}
