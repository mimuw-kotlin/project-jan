package com.app.ui.sudokuComponents

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// I moved the menu buttons to a new class, to improve readability in the project.
// It contains buttons: Main Menu, Save, New Game, Pause
@Composable
fun Menu(onBack: () -> Unit, isPaused: Boolean, onClickSaveGame: () -> Unit, onClickNewGame:() -> Unit, onClickPause: () -> Unit, onClickNextGame: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFC5705D),
                contentColor = Color.White
            )
        ) {
            Text("Main Menu")
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Saving current state of the board to the database
        Button(
            onClick = onClickSaveGame,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFC5705D),
                contentColor = Color.White
            )
        ) {
            Text("Save progress")
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Displaying the basic board to the user.
        Button(
            onClick = onClickNewGame,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFC5705D),
                contentColor = Color.White
            )
        ) {
            Text("New Game")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = onClickNextGame,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFC5705D),
                contentColor = Color.White
            )
        ){
            Text("Next Game")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = onClickPause,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFC5705D),
                contentColor = Color.White
            )
        ){
            Text(if(isPaused) "Unpause" else "Pause")
        }
    }
}