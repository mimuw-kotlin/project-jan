package com.example

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.backend.database.entities.SudokuBoards
import com.example.backend.database.services.SudokuService.insertSudoku
import com.example.backend.sudoku.Node
import com.example.backend.sudoku.SudokuBoard
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

fun populate() = runBlocking {

    database.DatabaseConfig.init()

    //Example sudoku, in the future could be replaced with own logi of generating sudoku
    var board = mutableListOf<MutableList<Int>>(
    mutableListOf(0, 0, 5, 0, 0, 0, 7, 0, 0, 0, 4),
    mutableListOf(6, 0, 9, 0, 3, 1, 0, 5, 0),
    mutableListOf(8, 0, 0, 0, 0, 9, 0, 1, 0),
    mutableListOf(4, 9, 0, 1, 0, 0, 8, 0, 2),
    mutableListOf(2, 0, 8, 0, 0, 6, 7, 0, 5),
    mutableListOf(0, 0, 0, 2, 8, 4, 1, 0, 0),
    mutableListOf(0, 7, 4, 0, 0, 8, 5, 6, 0),
    mutableListOf(1, 0, 0, 7, 6, 3, 4, 2, 0),
    mutableListOf(9, 6, 0, 0, 0, 0, 0, 0, 0)
    )

    var board2 = SudokuBoard()
    for (i in 0..<9){
        for (j in 0..<9){
            board2.content[i][j] = Node(i, j, board[i][j], true)
        }
    }

    Database.connect(
        url = "jdbc:sqlite:backend.db",
        driver = "org.sqlite.JDBC"
    )
    insertSudoku(board2.serialize())

}