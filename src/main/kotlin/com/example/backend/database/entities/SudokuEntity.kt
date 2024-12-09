package com.example.backend.database.entities

import org.jetbrains.exposed.sql.Table

//The board is stored as a string, serializing and deserializing is provided by SudokuBoard class
//id 1 is the game which is currently modified, id 2 is the base game, which is stored to provide 'new game' functionality.
object SudokuBoards : Table() {
    val id = integer("id")
    val board = text("board")
    override val primaryKey = PrimaryKey(id)
}