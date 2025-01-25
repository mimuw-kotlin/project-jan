package com.app

import addMyTime
import com.app.backend.database.entities.Rankings
import com.app.backend.database.entities.SudokuBoards
import com.app.backend.database.services.RankingService.addRanking
import com.app.backend.database.services.SudokuService.getSudokuById
import com.app.backend.database.services.SudokuService.getSudokuTimeById
import com.app.backend.database.services.SudokuService.insertSudoku
import com.app.backend.database.services.SudokuService.updateSudoku
import com.app.backend.sudoku.Node
import com.app.backend.sudoku.SudokuBoard
import getRankings
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.ArrayList


class BackendTest {

    @Test
    fun testPopulateAndUpdateSudokuBoard(): Unit = runBlocking {
        populate() //Clears database and then inserts boards and rankings

        val boardNodesValues1 = mutableListOf<MutableList<Int>>(
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
        val boardNodesValues2 = mutableListOf<MutableList<Int>>(
            mutableListOf(0, 0, 0, 1, 2, 0, 0, 0, 0),
            mutableListOf(1, 6, 0, 5, 0, 9, 0, 0, 0),
            mutableListOf(0, 0, 8, 7, 4, 6, 0, 0, 9),
            mutableListOf(9, 1, 0, 0, 7, 2, 0, 6, 0),
            mutableListOf(0, 0, 7, 0, 0, 0, 3, 0, 0),
            mutableListOf(0, 0, 0, 6, 3, 8, 1, 9, 7),
            mutableListOf(0, 0, 0, 2, 0, 0, 0, 0, 3),
            mutableListOf(0, 0, 0, 0, 0, 0, 8, 0, 0),
            mutableListOf(0, 4, 0, 0, 1, 0, 9, 0, 0)
        )

        var board1 = SudokuBoard.deserialize(getSudokuById(1)!!)
        val board2 = SudokuBoard.deserialize(getSudokuById(3)!!)

        // Checking if inserting to database and getting board from database is correct
        assertEquals(boardNodesValues1.toString(), board1.content.toString())
        assertEquals(boardNodesValues2.toString(), board2.content.toString())

        var time1 = getSudokuTimeById(1)
        assertEquals(time1, 0)


        //Checking if updating sudoku and time works correctly
        updateSudoku(board2.serialize(), 1, 100)
        board1 = SudokuBoard.deserialize(getSudokuById(1)!!)
        assertEquals(boardNodesValues2.toString(), board1.content.toString())

        time1 = getSudokuTimeById(1)
        assertEquals(100, time1)
    }

    //Checking basic operations for Rankings table.
    @Test
    fun testPopulateAndUpdateRankings(): Unit = runBlocking{
        transaction {
            SudokuBoards.deleteAll()
            Rankings.deleteAll()
        }

        addRanking(boardId = 1, timeMillis = 100)
        addRanking(boardId = 2, timeMillis = 1000)
        addRanking(boardId = 2, timeMillis = 2000)

        val ranks1 = getRankings(1)
        val ranks2 = getRankings(2)

        assertEquals(arrayListOf(100).toString(), ranks1.toString())
        assertEquals(arrayListOf(1000, 2000).toString(), ranks2.toString())
    }

}