package database

import com.example.backend.database.DatabaseDispatcher
import com.example.backend.database.entities.SudokuBoards
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseConfig {
    suspend fun init() = withContext(DatabaseDispatcher) {
        // Connecting to SQLite
        Database.connect(
            url = "jdbc:sqlite:backend.db",
            driver = "org.sqlite.JDBC"
        )

        transaction {
            SchemaUtils.create(SudokuBoards)
        }
    }
}
