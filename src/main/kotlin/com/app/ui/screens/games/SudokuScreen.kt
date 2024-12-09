import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.backend.database.services.SudokuService.getSudokuById
import com.app.backend.database.services.SudokuService.updateSudoku
import com.app.backend.sudoku.SudokuBoard
import com.app.ui.sudokuComponents.SudokuBoardUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep

@Composable
fun SudokuScreen(onBack: () -> Unit) {
    // State of the board, currently selected cell and number
    var selectedCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var selectedNumber by remember { mutableStateOf<Int?>(null) }
    var board by remember { mutableStateOf<SudokuBoard?>(null) }
    // var showPopup by remember { mutableStateOf(false) } TODO in the future
    // Is the board completed
    var completed by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Loading the initial board - for now errors are displayed in the console, should be developed further
    LaunchedEffect(Unit) {
        try {
            board = getInitialSudoku()
        } catch (e: Exception) {
            println("Error during initialization: ${e.message}")
        }
    }

    Column(
        modifier = Modifier
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sudoku", fontSize = 28.sp, modifier = Modifier.padding(8.dp))

        Row(
            modifier = Modifier
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SudokuBoardUI(
                sudokuBoard = board,
                selectedNode = selectedCell,
                completed = completed,
                onNodeClick = { x, y ->
                    selectedCell = Pair(x, y)
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            NumberPad(
                onNumberClick = { number ->
                    selectedNumber = number
                    selectedCell?.let { (x, y) ->
                        if (selectedNumber != null) {
                            scope.launch {
                                val updatedBoard = updateCell(board, x, y, selectedNumber!!)
                                board = updatedBoard.first
                                completed = updatedBoard.second
                            }
                        }
                    }
                }
            )
        }

        Row(
            modifier = Modifier
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFC5705D),
                    contentColor = Color.White
                )
            ) {
                Text("Main Menu")
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Saving current state of the board to the database
            Button(
                onClick = {
                    scope.launch {
                        val temp = board
                        board = null
                        board = saveSudoku(temp)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFC5705D),
                    contentColor = Color.White
                )
            ) {
                Text("Save progress")
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Displaying the basic board to the user.
            Button(
                onClick = {
                    scope.launch {
                        try {
                            board = null
                            board = newGame()
                        } catch (e: Exception) {
                            println("Error during creating new game: ${e.message}")
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFC5705D),
                    contentColor = Color.White
                )
            ) {
                Text("New game")
            }
        }
    }
}

// Updating board state
fun updateCell(board: SudokuBoard?, x: Int, y: Int, number: Int): Pair<SudokuBoard, Boolean> {
    if (!board!!.content[x][y].generated) {
        val newBoard = board.content.toMutableList().apply {
            this[x] = this[x].toMutableList().apply {
                this[y] = this[y].copy(number = number)
            }
        }
        val result = SudokuBoard(newBoard)
        result.validate()
        return Pair(result, result.isBoardValid())
    }
    return Pair(board, false)
}

// Coroutines use is unnecessary - just to provide an example of communication with the database.
// In my project it is really fast, but potentially- interactions with database shouldn't block the main thread.
// I manually add sleep() to simulate a longer process.
suspend fun getInitialSudoku(): SudokuBoard {
    return withContext(Dispatchers.IO) {
        sleep(1000) // To show that coroutines work :)
        val result = SudokuBoard.deserialize(getSudokuById(1)!!)
        result.validate()
        result
    }
}

// Error handling should be furtehr developped
suspend fun saveSudoku(board: SudokuBoard?): SudokuBoard? {
    if (board != null) {
        try {
            withContext(Dispatchers.IO) {
                sleep(1000)
                updateSudoku(board.serialize(), 1)
            }
        } catch (e: Exception) {
            println("Error saving sudoku: ${e.message}")
        }
    }
    return board
}

suspend fun newGame(): SudokuBoard {
    return withContext(Dispatchers.IO) {
        sleep(1000) // To show that coroutines work :)
        val sudoku = getSudokuById(2)!!
        updateSudoku(sudoku, 1)
        SudokuBoard.deserialize(sudoku)
    }
}
