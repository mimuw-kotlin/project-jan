package com.example.backend.database.entities

import org.jetbrains.exposed.sql.Table

object SudokuBoards : Table() {
    val id = integer("id")
    val board = text("board")
    override val primaryKey = PrimaryKey(id)
}