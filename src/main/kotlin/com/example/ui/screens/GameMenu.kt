package com.example.ui.screens

import SudokuScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

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
    val games = listOf("Sudoku", "Mastermind", "Tic Tac Toe")
    var currentIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Main Menu",
            fontSize = 50.sp,
            color = Color.Black,
            fontFamily = FontFamily.Serif
        )

        Spacer(modifier = Modifier.height(100.dp))

        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(300.dp)
                .background(Color(0xFFD0B8A8), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = games[currentIndex],
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFC5705D),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                //Navigation between games
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { currentIndex = if (currentIndex > 0) currentIndex - 1 else games.size - 1 },
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFC5705D),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Previous")
                    }

                    Button(
                        onClick = { currentIndex = (currentIndex + 1) % games.size },
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFC5705D),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Next")
                    }
                }
                //Selecting game
                Button(
                    onClick = {
                        when (currentIndex) {
                            0 -> onGameSelect(Game.SUDOKU)
                            1 -> onGameSelect(Game.MASTERMIND)
                            2 -> onGameSelect(Game.TIC_TAC_TOE)
                        }
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .width(150.dp)
                        .height(75.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFC5705D),
                        contentColor = Color.White
                    )
                ) {
                    Text("Start", color = Color.White, fontSize = 20.sp)
                }
            }
        }
    }
}