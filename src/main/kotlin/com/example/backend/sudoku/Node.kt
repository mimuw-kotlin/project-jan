package com.example.backend.sudoku
import kotlinx.serialization.Serializable


@Serializable
data class Node (
    val x: Int,
    val y: Int,
    var number: Int = 0,
    var generated: Boolean = false,
    var isValid: Boolean = true,
) {
    override fun toString(): String {
        return number.toString()
    }
}