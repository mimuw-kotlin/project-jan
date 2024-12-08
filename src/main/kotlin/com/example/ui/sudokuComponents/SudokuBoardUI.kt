import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.backend.sudoku.SudokuBoard

@Composable
fun SudokuBoardUI(
    sudokuBoard: SudokuBoard?,
    selectedNode: Pair<Int, Int>?,
    onNodeClick: (Int, Int) -> Unit,
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // Loop over each row in the Sudoku board
        when(sudokuBoard){
            null -> {Text("Loading...")}
            else -> {
                for (row in 0 until 9) {
                    Row (){
                        Divider(
                            color = Color.Black,
                            thickness = 2.dp,
                            modifier = Modifier
                                .height(2.dp)
                                .width(384.dp)
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        // Loop over each node in the current row
                        Divider(
                            color = Color.Black,
                            thickness = 2.dp,
                            modifier = Modifier
                                .height(40.dp)
                                .width(2.dp)
                        )
                        for (col in 0 until 9) {
                            val nodeValue = sudokuBoard.content[row][col]
                            val isSelected = selectedNode == Pair(row, col)
                            SudokuNode(
                                value = nodeValue.number,
                                isSelected = isSelected,
                                onClick = { onNodeClick(row, col) }
                            )
                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(2.dp)
                            )
                            // Add vertical lines between the nodes
                        }
                    }

                }
                Row (){
                    Divider(
                        color = Color.Black,
                        thickness = 2.dp,
                        modifier = Modifier
                            .height(2.dp)
                            .width(384.dp)
                    )
                }
            }
        }
    }
}

//Single Sudoku node
@Composable
fun SudokuNode(
    value: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clickable(onClick = onClick)
            .background(
                color = if (isSelected){
                    Color(0xFF00FA9A)
                }else{
                    Color.Transparent
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (value == 0) "" else value.toString(),
            fontSize = 18.sp,
            color = if (isSelected) Color.Blue else Color.Black
        )
    }
}

