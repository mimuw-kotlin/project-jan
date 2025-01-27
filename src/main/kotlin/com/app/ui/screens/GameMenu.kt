package com.app.ui.screens

import SudokuScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.ui.screens.games.MastermindScreen
import com.app.ui.screens.games.TicTacToeScreen

enum class Screen {
    MENU,
    SUDOKU,
    MASTERMIND,
    TIC_TAC_TOE,
}

@Composable
fun GameMenu() {
    var currentScreen by remember { mutableStateOf(Screen.MENU) }

    when (currentScreen) {
        Screen.MENU ->
            MenuScreen { selectedGame ->
                currentScreen = selectedGame
            }
        Screen.SUDOKU -> SudokuScreen { currentScreen = Screen.MENU }
        Screen.MASTERMIND -> MastermindScreen { currentScreen = Screen.MENU }
        Screen.TIC_TAC_TOE -> TicTacToeScreen { currentScreen = Screen.MENU }
    }
}

@Composable
fun MenuScreen(onGameSelect: (Screen) -> Unit) {
    val screens = listOf("Sudoku", "Mastermind", "Tic Tac Toe")
    var currentIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Main Menu",
            fontSize = 50.sp,
            color = Color.Black,
            fontFamily = FontFamily.Serif,
        )

        Spacer(modifier = Modifier.height(100.dp))
        Box(
            modifier =
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color(0xFFD0B8A8), RoundedCornerShape(16.dp))
                    .padding(16.dp),
        ) {
            Column(
                modifier =
                    Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = screens[currentIndex],
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFC5705D),
                    modifier = Modifier.padding(bottom = 16.dp),
                )

                // Navigation between games
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(
                        onClick = { currentIndex = if (currentIndex > 0) currentIndex - 1 else screens.size - 1 },
                        modifier = Modifier.padding(8.dp),
                        colors =
                            ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFFC5705D),
                                contentColor = Color.White,
                            ),
                    ) {
                        Text("Previous")
                    }

                    Button(
                        onClick = { currentIndex = (currentIndex + 1) % screens.size },
                        modifier = Modifier.padding(8.dp),
                        colors =
                            ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFFC5705D),
                                contentColor = Color.White,
                            ),
                    ) {
                        Text("Next")
                    }
                }
                // Selecting game
                Button(
                    onClick = {
                        when (currentIndex) {
                            0 -> onGameSelect(Screen.SUDOKU)
                            1 -> onGameSelect(Screen.MASTERMIND)
                            2 -> onGameSelect(Screen.TIC_TAC_TOE)
                        }
                    },
                    modifier =
                        Modifier
                            .padding(top = 16.dp)
                            .width(150.dp)
                            .height(75.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFC5705D),
                            contentColor = Color.White,
                        ),
                ) {
                    Text("Start", color = Color.White, fontSize = 20.sp)
                }
            }
        }
    }
}
