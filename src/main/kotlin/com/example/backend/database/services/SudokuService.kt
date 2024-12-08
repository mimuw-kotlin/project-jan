package com.example.backend.database.services

import com.example.backend.database.DatabaseDispatcher
import com.example.backend.database.entities.SudokuBoards
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object SudokuService {
    suspend fun insertSudoku(board: String) = withContext(DatabaseDispatcher) {
        transaction {
            SudokuBoards.insert {
                it[SudokuBoards.board] = board
            }
        }
    }

    suspend fun getSudokuById(id: Int): String? = withContext(DatabaseDispatcher) {
        transaction {
            SudokuBoards.select { SudokuBoards.id eq id }
                .map { it[SudokuBoards.board] }
                .singleOrNull()
        }
    }

    suspend fun getAllSudokus(): List<String> = withContext(DatabaseDispatcher) {
        transaction {
            SudokuBoards.selectAll()
                .map { it[SudokuBoards.board] }
        }
    }

    suspend fun updateSudoku(board: String, id: Int) = withContext(DatabaseDispatcher) {
        transaction {
            SudokuBoards.update({SudokuBoards.id eq id}) {
                it[SudokuBoards.board] = board
            }
        }
    }
}