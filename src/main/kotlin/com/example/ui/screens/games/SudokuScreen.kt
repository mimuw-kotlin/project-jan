import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.backend.sudoku.SudokuBoard

@Composable
fun SudokuScreen(onBack: () -> Unit) {
    // Przechowywanie stanu planszy, wybranej komórki i wybranego numeru
    var selectedCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var selectedNumber by remember { mutableStateOf<Int?>(null) }
    var board by remember { mutableStateOf(SudokuBoard()) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Nagłówek z tytułem
        Text("Sudoku", fontSize = 32.sp, modifier = Modifier.padding(16.dp))

        // Plansza Sudoku
        SudokuBoardUI(
            sudokuBoard = board,
            selectedCell = selectedCell,
            onCellClick = { x, y ->
                selectedCell = Pair(x, y) // Ustawienie wybranej komórki
            }
        )

        // Panel z liczbami do wyboru (1-9)
        NumberPad(onNumberClick = { number ->
            selectedNumber = number // Ustawienie wybranej liczby
            // Jeśli wybrano komórkę, zaktualizuj jej wartość
            selectedCell?.let { (x, y) ->
                // Tylko wtedy, gdy wybrano liczbę i komórkę
                if (selectedNumber != null) {
                    scope.launch {
                        val updatedBoard = updateCellAsync(board, x, y, selectedNumber!!)
                        board = updatedBoard // Zaktualizowanie planszy
                    }
                }
            }
        })

        Spacer(modifier = Modifier.height(16.dp))

        // Przycisk powrotu
        Button(onClick = onBack) {
            Text("Powrót")
        }
    }
}

suspend fun updateCellAsync(board: SudokuBoard, x: Int, y: Int, number: Int): SudokuBoard {
    return withContext(Dispatchers.Default) {
        // Tworzenie nowej planszy z aktualizowaną wartością w komórce
        val newBoard = board.content.toMutableList().apply {
            this[x] = this[x].toMutableList().apply {
                this[y] = this[y].copy(number = number) // Aktualizowanie komórki
            }
        }
        SudokuBoard(newBoard) // Zwracamy nową planszę
    }
}
