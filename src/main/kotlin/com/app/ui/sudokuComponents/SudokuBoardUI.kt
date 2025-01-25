package com.app.ui.sudokuComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.backend.sudoku.CellCoordinates
import com.app.backend.sudoku.SudokuBoard

@Composable
fun SudokuBoardUI(
    sudokuBoard: SudokuBoard?,
    selectedNode: CellCoordinates?,
    onNodeClick: (Int, Int) -> Unit,
    completed: Boolean,
    isPaused: Boolean,
    rankings: List<Long>
) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        // Checking if the board is loading
        if(sudokuBoard == null){
            Box( // Simulation of long communication, loading screen
                modifier = Modifier
                    .width(fullLen)
                    .height(fullLen),
                contentAlignment = Alignment.Center
            ) {
                Text("Loading...", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
            }
        }else if(!isPaused){
            //Checking if the board is completed.
            if (completed) {
                Box(
                    modifier = Modifier
                        .width(fullLen)
                        .height(fullLen),
                    contentAlignment = Alignment.Center
                ) {
                    Text("CONGRATULATIONS", fontSize = 40.sp)
                }
            } else {
                // Displaying the whole board
                for (row in 0 until 9) {
                    Row() {
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
                        Divider(
                            color = dividerColor,
                            modifier = Modifier
                                .height(cellWidth)
                                .width(dividerWidth)
                        )
                        for (col in 0 until 9) {
                            val nodeValue = sudokuBoard.content[row][col]
                            val isSelected = selectedNode == CellCoordinates(row, col)
                            var isAdjacentToSelected = false
                            var isAdjacentSquare = false
                            if (selectedNode != null) {
                                isAdjacentToSelected = (selectedNode.x == row) || (selectedNode.y == col)
                                isAdjacentSquare =
                                    (row / 3) * 3 + (col / 3) == (selectedNode.x / 3) * 3 + (selectedNode.y / 3)
                            }

                            SudokuNode(
                                value = nodeValue.number,
                                isSelected = isSelected,
                                onClick = { onNodeClick(row, col) },
                                isAdjacentToSelected = isAdjacentToSelected,
                                isAdjacentSquare = isAdjacentSquare,
                                isValid = nodeValue.isValid,
                                isGenerated = nodeValue.generated,
                                notes = nodeValue.notes
                            )
                            Divider(
                                color = dividerColor,
                                modifier = Modifier
                                    .height(cellWidth)
                                    .width(if (col % 3 == 2) dividerWidth else thinDividerWidth)
                            )
                        }
                    }
                }
                Row() {
                    Divider(
                        color = Color.Black,
                        thickness = dividerWidth,
                        modifier = Modifier
                            .height(dividerWidth)
                            .width(fullLen)
                    )
                }
            }
        }else{
            // Displaying the ranking
            Column(
                modifier = Modifier
                    .width(fullLen)
                    .height(fullLen),
                Arrangement.SpaceEvenly,
            ) {
                // For now always top 10 players.
                for (i in 1..10) {
                    Row(modifier = Modifier.padding(start = 10.dp)) {
                        if(rankings.size >= i){
                            Text("${i}: ${DisplayTime(rankings[i - 1])}", modifier = Modifier.padding(start = 10.dp), fontSize = 20.sp)
                        }else{
                            Text("${i}: None", modifier = Modifier.padding(start = 10.dp), fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }
}

// Single Sudoku node
@Composable
fun SudokuNode(
    value: Int,
    isSelected: Boolean,
    isAdjacentToSelected: Boolean,
    isAdjacentSquare: Boolean,
    onClick: () -> Unit,
    isValid: Boolean,
    isGenerated: Boolean,
    notes: MutableSet<Int>
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clickable(onClick = onClick)
            .background(
                color = if (!isValid) {
                    Color.Red
                } else if (isSelected) {
                    selectedCellColor
                } else if (isAdjacentToSelected || isAdjacentSquare) {
                    adjacentColor
                } else {
                    Color.Transparent
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        //Displaying notes
        if(notes.size == 0){
            Text(
                text = if (value > 0) value.toString() else "",
                fontSize = 18.sp,
                color = if(isGenerated) Color.Black else userColor,
            )
        }else{
            Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                for (row in 0 until 3) {
                    Row() {
                        for (col in 1 until 4) {
                            val number = row * 3 + col
                            Box(modifier = Modifier.size(13.dp),
                                    contentAlignment = Alignment.Center) {
                                if(notes.contains(number)){
                                    Text("$number", fontSize = 8.sp, color = userColor)
                                }
                            }
                        }

                    }
                }
            }
        }

    }
}
