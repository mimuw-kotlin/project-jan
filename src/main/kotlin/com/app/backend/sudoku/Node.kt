package com.app.backend.sudoku
import kotlinx.serialization.Serializable

// Representation of sudoku node. x,y: coordinates on the board, number: value of the node, if equal to 0- no number
// generated: if a node was generated (during game creation) it cannot be modified.
// isValid: if a player inputs a number, it can invalidate the board/cell. If a number collides with another number due
// to sudoku rules, it is not valid, and displayed with red background.

@Serializable
data class Node(
    val x: Int,
    val y: Int,
    var number: Int = 0,
    var generated: Boolean = false,
    var isValid: Boolean = true
) {
    override fun toString(): String {
        return number.toString()
    }
}
