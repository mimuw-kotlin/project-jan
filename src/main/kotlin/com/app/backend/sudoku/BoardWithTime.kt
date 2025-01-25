package com.app.backend.sudoku

// Pair representing response containing sudoku board and current time
data class BoardWithTime (
    val board: SudokuBoard,
    val time: Long
)