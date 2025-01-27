package com.app.backend.database.entities

import org.jetbrains.exposed.sql.Table

// The ranking is associated with a specific Sudoku board and the time in milliseconds
object Rankings : Table() {
    val id = integer("id").autoIncrement()
    val boardId = integer("board_id")
    val timeMillis = long("time_millis")

    // Define the primary key for this table
    override val primaryKey = PrimaryKey(id)
}
