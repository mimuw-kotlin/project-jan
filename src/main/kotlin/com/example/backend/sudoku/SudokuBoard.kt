package com.example.backend.sudoku

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

data class SudokuBoard(
    val content: MutableList<MutableList<Node>> = MutableList(9) { x ->
        MutableList(9) { y ->
            Node(x, y)
        }
    }
) {

    // Checking if a number is colliding with any number due to sudoku rules.
    private fun isValidNumber(x: Int, y: Int, number: Int): Boolean {
        if (content[x].any { it.number == number && it.y != y }) {
            return false
        }

        if (content.any { it[y].number == number && it != content[x] }) {
            return false
        }

        val startRow = (x / 3) * 3
        val startCol = (y / 3) * 3
        for (row in startRow until startRow + 3) {
            for (col in startCol until startCol + 3) {
                if (content[row][col].number == number && (row != x || col != y)) {
                    return false
                }
            }
        }
        return true
    }

    // Checking if the whole board is valid- if the user won.
    fun isBoardValid(): Boolean {
        for (row in content) {
            for (node in row) {
                if (!isValidNumber(node.x, node.y, node.number) || node.number == 0) {
                    return false
                }
            }
        }
        return true
    }

    // Updating the whole board, checking if nodes collide with something or not.
    fun validate() {
        for (row in content) {
            for (node in row) {
                node.isValid = if (node.number != 0) isValidNumber(node.x, node.y, node.number) else true
            }
        }
    }

    // Serializing and deserializing between JSON and string.
    fun serialize(): String {
        return Json.encodeToString(content)
    }

    companion object {
        fun deserialize(json: String): SudokuBoard {
            val nodes = Json.decodeFromString<List<List<Node>>>(json)
            val board = SudokuBoard()
            for (row in nodes.indices) {
                for (col in nodes[row].indices) {
                    board.content[row][col] = nodes[row][col]
                }
            }
            return board
        }
    }
}
