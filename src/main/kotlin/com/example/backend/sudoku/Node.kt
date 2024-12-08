package com.example.backend.sudoku

data class Node (
    val x: Int,
    val y: Int,
    var number: Int = 0,
    var generated: Boolean = false,
) {
    override fun toString(): String {
        return number.toString()
    }
}