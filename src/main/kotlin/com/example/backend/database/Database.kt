package com.example.backend.database

import com.example.backend.database.entities.SudokuBoards
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseConfig {
    fun init() {
        Database.connect(
            url = "jdbc:sqlite:backend.db",
            driver = "org.sqlite.JDBC"
        )

        transaction {
            SchemaUtils.create(SudokuBoards)
        }
    }
}
