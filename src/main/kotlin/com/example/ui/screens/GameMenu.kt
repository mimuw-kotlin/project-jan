package com.example.ui.screens

import SudokuScreen
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.screens.games.MastermindScreen
import com.example.ui.screens.games.TicTacToeScreen

enum class Game {
    MENU, SUDOKU, MASTERMIND, TIC_TAC_TOE
}

@Composable
fun GameMenu() {
    var currentScreen by remember { mutableStateOf(Game.MENU) }

    when (currentScreen) {
        Game.MENU -> MenuScreen { selectedGame ->
            currentScreen = selectedGame
        }
        Game.SUDOKU -> SudokuScreen { currentScreen = Game.MENU }
        Game.MASTERMIND -> MastermindScreen { currentScreen = Game.MENU }
        Game.TIC_TAC_TOE -> TicTacToeScreen { currentScreen = Game.MENU }
    }
}

@Composable
fun MenuScreen(onGameSelect: (Game) -> Unit) {
    val games = listOf("Sudoku", "Mastermind", "Tick Tac Toe")
    var currentIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = games[currentIndex],
            fontSize = 32.sp,
            modifier = Modifier.padding(16.dp)
        )

        Row {
            Button(
                onClick = { currentIndex = if (currentIndex > 0) currentIndex - 1 else games.size - 1 },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Previous")
            }

            Button(
                onClick = { currentIndex = (currentIndex + 1) % games.size },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Next")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                when (currentIndex) {
                    0 -> onGameSelect(Game.SUDOKU)
                    1 -> onGameSelect(Game.MASTERMIND)
                    2 -> onGameSelect(Game.TIC_TAC_TOE)
                }
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Start")
        }
    }
}
