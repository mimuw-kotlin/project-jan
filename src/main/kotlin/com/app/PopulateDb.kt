package com.app

import com.app.backend.database.entities.Rankings
import com.app.backend.database.entities.SudokuBoards
import com.app.backend.database.services.RankingService.addRanking
import com.app.backend.database.services.SudokuService.insertSudoku
import com.app.backend.sudoku.Node
import com.app.backend.sudoku.SudokuBoard
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction

fun populate() =
    runBlocking {
        database.DatabaseConfig.init()

        // Example sudoku, in the future could be replaced with own logic of generating sudoku
        val boardNodesValues1 =
            mutableListOf<MutableList<Int>>(
                mutableListOf(5, 0, 0, 0, 7, 0, 0, 0, 4),
                mutableListOf(6, 0, 9, 0, 3, 1, 0, 5, 0),
                mutableListOf(8, 0, 0, 0, 0, 9, 0, 1, 0),
                mutableListOf(4, 9, 0, 1, 0, 0, 8, 0, 2),
                mutableListOf(2, 0, 8, 0, 0, 6, 7, 0, 5),
                mutableListOf(0, 0, 0, 2, 8, 4, 1, 0, 0),
                mutableListOf(0, 7, 4, 0, 0, 8, 5, 6, 0),
                mutableListOf(1, 0, 0, 7, 6, 3, 4, 2, 0),
                mutableListOf(9, 6, 0, 0, 0, 0, 0, 0, 0),
            )

        val boardNodesValues2 =
            mutableListOf<MutableList<Int>>(
                mutableListOf(0, 0, 0, 1, 2, 0, 0, 0, 0),
                mutableListOf(1, 6, 0, 5, 0, 9, 0, 0, 0),
                mutableListOf(0, 0, 8, 7, 4, 6, 0, 0, 9),
                mutableListOf(9, 1, 0, 0, 7, 2, 0, 6, 0),
                mutableListOf(0, 0, 7, 0, 0, 0, 3, 0, 0),
                mutableListOf(0, 0, 0, 6, 3, 8, 1, 9, 7),
                mutableListOf(0, 0, 0, 2, 0, 0, 0, 0, 3),
                mutableListOf(0, 0, 0, 0, 0, 0, 8, 0, 0),
                mutableListOf(0, 4, 0, 0, 1, 0, 9, 0, 0),
            )

        val board1 = SudokuBoard()
        for (i in 0..8) {
            for (j in 0..8) {
                board1.content[i][j] = Node(i, j, boardNodesValues1[i][j], generated = boardNodesValues1[i][j] != 0)
            }
        }

        val board2 = SudokuBoard()
        for (i in 0..8) {
            for (j in 0..8) {
                board2.content[i][j] = Node(i, j, boardNodesValues2[i][j], generated = boardNodesValues2[i][j] != 0)
            }
        }

        transaction {
            SudokuBoards.deleteAll()
            Rankings.deleteAll()
        }

        // id 1: current sudoku, id 2: base, non-modifiable sudoku
        insertSudoku(board1.serialize(), 1)
        insertSudoku(board1.serialize(), 2)

        insertSudoku(board2.serialize(), 3)
        insertSudoku(board2.serialize(), 4)

        addRanking(1, 1000000L)
        addRanking(1, 500000L)

        addRanking(3, 2000000L)
        addRanking(3, 300000L)
    }
