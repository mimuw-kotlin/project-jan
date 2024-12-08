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
    sudokuBoard: SudokuBoard,
    selectedCell: Pair<Int, Int>?,
    onCellClick: (Int, Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // Loop over each row in the Sudoku board

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
                horizontalArrangement = Arrangement.spacedBy(0.dp) // No space between cells
            ) {
                // Loop over each cell in the current row
                Divider(
                    color = Color.Black,
                    thickness = 2.dp,
                    modifier = Modifier
                        .height(40.dp)
                        .width(2.dp)
                )
                for (col in 0 until 9) {
                    val cellValue = sudokuBoard.content[row][col]
                    val isSelected = selectedCell == Pair(row, col)

                    // Display the cell without borders
                    SudokuCell(
                        value = cellValue.number,
                        isSelected = isSelected,
                        onClick = { onCellClick(row, col) }
                    )
                    Divider(
                        color = Color.Black,
                        thickness = 2.dp,
                        modifier = Modifier
                            .height(40.dp)
                            .width(2.dp)
                    )
                    // Add vertical lines between the cells (excluding last column)
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

@Composable
fun SudokuCell(
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

