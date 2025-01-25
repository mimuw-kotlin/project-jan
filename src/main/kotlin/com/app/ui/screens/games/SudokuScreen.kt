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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.backend.database.entities.SudokuBoards.currentTime
import com.app.backend.database.services.RankingService.addRanking
import com.app.backend.database.services.RankingService.getRankingsForBoard
import com.app.backend.database.services.SudokuService.getSudokuById
import com.app.backend.database.services.SudokuService.getSudokuTimeById
import com.app.backend.database.services.SudokuService.updateSudoku
import com.app.backend.sudoku.BoardStatus
import com.app.backend.sudoku.BoardWithTime
import com.app.backend.sudoku.CellCoordinates
import com.app.backend.sudoku.SudokuBoard
import com.app.ui.sudokuComponents.Menu
import com.app.ui.sudokuComponents.SudokuBoardUI
import com.app.ui.sudokuComponents.Timer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep

@Composable
fun SudokuScreen(onBack: () -> Unit) {
    // State of the board, currently selected cell and number
    var selectedCell by remember { mutableStateOf<CellCoordinates?>(null) }
    var selectedNumber by remember { mutableStateOf<Int?>(null) }
    var board by remember { mutableStateOf<SudokuBoard?>(null) }
    // Is the board completed
    var completed by remember { mutableStateOf(false) }
    // Is editing notes enabled.
    var isEditingNotes by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var isPaused by remember { mutableStateOf(true) }
    var currentBoard by remember { mutableStateOf(1) }
    var currentTime by remember { mutableStateOf<Long>(0) }
    var rankings by remember { mutableStateOf<List<Long>>(emptyList()) }

    // Loading the initial board - for now errors are displayed in the console, should be developed further
    LaunchedEffect(Unit) {
        try {
            rankings = getRankings(currentBoard)
            val boardWithTime = getSudoku(currentBoard)
            board = boardWithTime.board
            currentTime = boardWithTime.time
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
                    selectedCell = CellCoordinates(x, y)
                },
                isPaused = isPaused,
                rankings = rankings
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Timer(isPaused, currentTime, updateTime = {delta -> currentTime += delta})
                NumberPad(
                    onNumberClick = { number ->
                        if (number in 0..9) {
                            selectedNumber = number
                            selectedCell?.let { (x, y) ->
                                scope.launch {
                                    val updatedBoard = updateCell(board!!, x, y, number, isEditingNotes, scope, currentTime, currentBoard)
                                    board = updatedBoard.board
                                    if (updatedBoard.completed){
                                        isPaused = true
                                    }
                                    completed = updatedBoard.completed
                                }
                            }
                        }else if(number == -1){
                            isEditingNotes = !isEditingNotes
                        }
                    }, isEditingNotes
                )
            }
        }
        Menu(onBack, isPaused,
            onClickSaveGame = {
                scope.launch {
                    isPaused = true
                    val temp = board
                    board = null
                    board = saveSudoku(temp!!, currentBoard, currentTime)
                }
            }, onClickNewGame = {
                scope.launch {
                    try {
                        board = null
                        isPaused = true
                        currentTime = 0
                        board = newGame(currentBoard)
                    } catch (e: Exception) {
                        println("Error during creating new game: ${e.message}")
                    }
                }
            }, onClickPause = {
                isPaused = !isPaused
            }, onClickNextGame = {
                scope.launch {
                    board = null
                    isPaused = true
                    currentTime = 0
                    currentBoard = (currentBoard + 2) % 4
                    rankings = getRankings(currentBoard)
                    val boardWithTime = getSudoku(currentBoard)
                    board = boardWithTime.board
                    currentTime = boardWithTime.time
                }
            }
        )
    }
}

// Updating board state: two cases, when editing board and editing nodes.
// Editing nodes has to reload the board, so we change the number in the node but don't validate.
// Additionally, operations like setting a number or clearing a cell deletes all notes.
fun updateCell(board: SudokuBoard, x: Int, y: Int, number: Int, isEditingBoard: Boolean, scope: CoroutineScope, currentTime: Long, currentBoard: Int): BoardStatus {
    if (!board.content[x][y].generated) {
        if (!isEditingBoard){
            val newBoard = board.content.toMutableList().apply {
                this[x] = this[x].toMutableList().apply {
                    this[y] = this[y].copy(number = number, notes = mutableSetOf())
                }
            }
            val result = SudokuBoard(newBoard)
            result.validate()
            // If the board is completed, add time to rankings.
            val completed = result.isBoardValid()
            if (completed) {
                scope.launch {
                    addMyTime(currentTime, currentBoard)
                }
            }
            return BoardStatus(result, completed)
        }else{
            val newBoard = board.content.toMutableList().apply {
                this[x] = this[x].toMutableList().apply {
                    if(number == 0){
                        this[y] = this[y].copy(number = number, notes = mutableSetOf())
                    }else{
                        if(number == this[y].number){
                            this[y] = this[y].copy(number = -this[y].number, notes = this[y].changeNote(number))
                        }else{
                            this[y] = this[y].copy(number = number, notes = this[y].changeNote(number))
                        }
                    }
                }
            }
            val result = SudokuBoard(newBoard)
            return BoardStatus(result, false)
        }
    }
    return BoardStatus(board, false)
}

// Coroutines use is unnecessary - just to provide an example of communication with the database.
// In my project it is really fast, but potentially- interactions with database shouldn't block the main thread.
// I manually add sleep() to simulate a longer process.
suspend fun getSudoku(boardNumber: Int): BoardWithTime {
    return withContext(Dispatchers.IO) {
        sleep(1000) // To show that coroutines work :)
        val result = SudokuBoard.deserialize(getSudokuById(boardNumber)!!)
        result.validate()
        val time = getSudokuTimeById(boardNumber)
        BoardWithTime(result, time ?: 0)
    }
}

suspend fun saveSudoku(board: SudokuBoard, boardNumber: Int, currentTime: Long): SudokuBoard {
    try {
        withContext(Dispatchers.IO) {
            sleep(1000)
            updateSudoku(board.serialize(), boardNumber, currentTime = currentTime)
        }
    } catch (e: Exception) {
        println("Error saving sudoku: ${e.message}")
    }
    return board
}

suspend fun newGame(boardNumber: Int): SudokuBoard {
    return withContext(Dispatchers.IO) {
        sleep(1000) // To show that coroutines work :)
        val sudoku = getSudokuById(boardNumber + 1)!!
        updateSudoku(sudoku, boardNumber, 0)
        SudokuBoard.deserialize(sudoku)
    }
}

suspend fun addMyTime(time: Long, boardNumber: Int){
    withContext(Dispatchers.IO) {
        sleep(1000)
        addRanking(boardNumber, time)
    }
}

suspend fun getRankings(boardNumber: Int): List<Long>{
    return withContext(Dispatchers.IO) {
        sleep(1000) // To show that coroutines work :)
        val result = getRankingsForBoard(boardNumber, 10)
        result
    }
}