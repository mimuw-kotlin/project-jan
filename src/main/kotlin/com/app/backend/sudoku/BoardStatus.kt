package com.app.backend.sudoku

// Data class with current state fo the board - nodes and information if the board is completed.
data class BoardStatus(
    var board: SudokuBoard?,
    var completed: Boolean
)