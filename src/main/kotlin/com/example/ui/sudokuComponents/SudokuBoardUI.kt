package com.example.ui.sudokuComponents

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
        modifier = Modifier.padding(8.dp)
    ) {
        // Loop over each row in the Sudoku board
        when(sudokuBoard){
            null -> {Box(
                modifier = Modifier
                    .width(fullLen)
                    .height(fullLen),
                contentAlignment = Alignment.Center
            ){
                Text("Loading...", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
            }}
            else -> {
                for (row in 0 until 9) {
                    Row (){
                        Divider(
                            color = dividerColor,
                            modifier = Modifier
                                .height(if (row % 3 == 0) dividerWidth else thinDividerWidth)
                                .width(fullLen)
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        // Loop over each node in the current row
                        Divider(
                            color = dividerColor,
                            modifier = Modifier
                                .height(cellWidth)
                                .width(dividerWidth)
                        )
                        for (col in 0 until 9) {
                            val nodeValue = sudokuBoard.content[row][col]
                            val isSelected = selectedNode == Pair(row, col)
                            var isAdjacentToSelected = false
                            var isAdjacentSquare = false
                            if (selectedNode != null) {
                                isAdjacentToSelected = (selectedNode.first == row) || (selectedNode.second == col)
                                isAdjacentSquare =
                                    (row / 3) * 3 + (col / 3) == (selectedNode.first / 3) * 3 + (selectedNode.second / 3)
                            }

                            SudokuNode(
                                value = nodeValue.number,
                                isSelected = isSelected,
                                onClick = { onNodeClick(row, col) },
                                isAdjacentToSelected = isAdjacentToSelected,
                                isAdjacentSquare = isAdjacentSquare
                            )
                            Divider(
                                color = dividerColor,
                                modifier = Modifier
                                    .height(cellWidth)
                                    .width(if (col % 3 == 2) dividerWidth else thinDividerWidth)
                            )
                            // Add vertical lines between the nodes
                        }
                    }

                }
                Row (){
                    Divider(
                        color = Color.Black,
                        thickness = dividerWidth,
                        modifier = Modifier
                            .height(dividerWidth)
                            .width(fullLen)
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
    isAdjacentToSelected: Boolean,
    isAdjacentSquare: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clickable(onClick = onClick)
            .background(
                color = if (isSelected){
                    selectedCellColor
                }else if (isAdjacentToSelected || isAdjacentSquare) {
                    adjacentColor
                }else{
                    Color.Transparent
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (value == 0) "" else value.toString(),
            fontSize = 18.sp,
            color = Color.Black
        )
    }
}

