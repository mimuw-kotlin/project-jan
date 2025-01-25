package com.app.backend.database.services

import com.app.backend.database.entities.SudokuBoards
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

// Basic functions that provide comunication with databse
object SudokuService {
    suspend fun insertSudoku(board: String, id: Int) = withContext(Dispatchers.IO) {
        transaction {
            SudokuBoards.insert {
                it[SudokuBoards.board] = board
                it[SudokuBoards.id] = id
                it[currentTime] = 0L
            }
        }
    }

    suspend fun getSudokuById(id: Int): String? = withContext(Dispatchers.IO) {
        transaction {
            SudokuBoards.selectAll().where { SudokuBoards.id eq id }
                .map { it[SudokuBoards.board] }
                .singleOrNull()
        }
    }

    suspend fun getSudokuTimeById(id: Int): Long? = withContext(Dispatchers.IO) {
        transaction {
            SudokuBoards.selectAll().where { SudokuBoards.id eq id }
                .map { it[SudokuBoards.currentTime] }
                .singleOrNull()
        }
    }

    suspend fun updateSudoku(board: String, id: Int, currentTime: Long) = withContext(Dispatchers.IO) {
        transaction {
            SudokuBoards.update({ SudokuBoards.id eq id }) {
                it[SudokuBoards.board] = board
                it[SudokuBoards.currentTime] = currentTime
            }
        }
    }
}
