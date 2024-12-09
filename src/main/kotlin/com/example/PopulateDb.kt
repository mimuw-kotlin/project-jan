package com.example

import com.example.backend.database.entities.SudokuBoards
import com.example.backend.database.services.SudokuService.insertSudoku
import com.example.backend.sudoku.Node
import com.example.backend.sudoku.SudokuBoard
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction

fun populate() = runBlocking {
    database.DatabaseConfig.init()

    // Example sudoku, in the future could be replaced with own logic of generating sudoku
    val boardNodesValues = mutableListOf<MutableList<Int>>(
        mutableListOf(5, 0, 0, 0, 7, 0, 0, 0, 4),
        mutableListOf(6, 0, 9, 0, 3, 1, 0, 5, 0),
        mutableListOf(8, 0, 0, 0, 0, 9, 0, 1, 0),
        mutableListOf(4, 9, 0, 1, 0, 0, 8, 0, 2),
        mutableListOf(2, 0, 8, 0, 0, 6, 7, 0, 5),
        mutableListOf(0, 0, 0, 2, 8, 4, 1, 0, 0),
        mutableListOf(0, 7, 4, 0, 0, 8, 5, 6, 0),
        mutableListOf(1, 0, 0, 7, 6, 3, 4, 2, 0),
        mutableListOf(9, 6, 0, 0, 0, 0, 0, 0, 0)
    )

    val board = SudokuBoard()
    for (i in 0..8) {
        for (j in 0..8) {
            board.content[i][j] = Node(i, j, boardNodesValues[i][j], generated = boardNodesValues[i][j] != 0)
        }
    }

    transaction {
        SudokuBoards.deleteAll()
    }

    // id 1: current sudoku, id 2: base, non-modifiable sudoku
    insertSudoku(board.serialize(), 1)
    insertSudoku(board.serialize(), 2)
}
