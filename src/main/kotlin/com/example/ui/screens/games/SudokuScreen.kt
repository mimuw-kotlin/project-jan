import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.backend.database.services.SudokuService.getSudokuById
import com.example.backend.sudoku.SudokuBoard
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
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        try {
            board = getInitialSudoku(1)
        } catch (e: Exception) {
            println("Error during initialization: ${e.message}")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sudoku", fontSize = 32.sp, modifier = Modifier.padding(16.dp))

        SudokuBoardUI(
            sudokuBoard = board,
            selectedNode = selectedCell,
            onNodeClick = { x, y ->
                selectedCell = Pair(x, y)
            }
        )

        NumberPad(onNumberClick = { number ->
            selectedNumber = number
            selectedCell?.let { (x, y) ->
                if (selectedNumber != null) {
                    scope.launch {
                        val updatedBoard = updateCell(board, x, y, selectedNumber!!)
                        board = updatedBoard
                    }
                }
            }
        })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBack) {
            Text("Back")
        }
    }
}

fun updateCell(board: SudokuBoard?, x: Int, y: Int, number: Int): SudokuBoard {
    val newBoard = board!!.content.toMutableList().apply {
        this[x] = this[x].toMutableList().apply {
            this[y] = this[y].copy(number = number)
        }
    }
    return SudokuBoard(newBoard)
}

suspend fun getInitialSudoku(id: Int) : SudokuBoard{
    return withContext(Dispatchers.IO){
        sleep(3000) //To show that coroutines work :)
        SudokuBoard.deserialize(getSudokuById(id)!!)
    }
}
