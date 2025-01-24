package com.app.backend.sudoku

data class BoardStatus(
    var board: SudokuBoard?,
    var completed: Boolean
)