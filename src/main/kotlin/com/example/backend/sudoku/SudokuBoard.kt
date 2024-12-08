package com.example.backend.sudoku

data class SudokuBoard(
    val content: MutableList<MutableList<Node>> = MutableList(9) { x ->
        MutableList(9) { y ->
            Node(x, y) // Wypełniamy wiersze i kolumny obiektami Node
        }
    }
) {


    fun isValidNumber(x: Int, y: Int, number: Int): Boolean {
        if (content[x].any { it.x == number && it.y != y }) return false

        if (content.any { it[y].number == number && it[x].x != x }) return false

        val startRow = (x / 3) * 3
        val startCol = (y / 3) * 3
        for (row in startRow until startRow + 3) {
            for (col in startCol until startCol + 3) {
                if (content[row][col].number == number && (row != x || col != y)) return false
            }
        }

        return true
    }

    fun isBoardValid(): Boolean {
        for (row in content) {
            for (node in row) {
                if (!isValidNumber(node.x, node.y, node.number)) {
                    return false
                }
            }
        }
        return true
    }


}
