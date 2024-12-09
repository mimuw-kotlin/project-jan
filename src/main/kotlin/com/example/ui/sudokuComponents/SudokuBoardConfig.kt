package com.example.ui.sudokuComponents

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Easy UI parameters modifications: mainly about design of the board
val cellWidth = 40.dp
val thinDividerWidth = 1.dp
val dividerWidth = 2.dp
val dividerColor = Color.Black
val selectedCellColor = Color(0xFFC5705D)
val adjacentColor = Color(0xFFD0B8A8)
val fullLen = dividerWidth * 4 + thinDividerWidth * 6 + cellWidth * 9
