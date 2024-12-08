package com.example.ui.sudokuComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun ResultPopup(showPopup: Boolean, success: Boolean, message: String?, onDismiss: () -> Unit) {
    if (showPopup) {
        Dialog(onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (success) "Success" else "Error",
                        style = MaterialTheme.typography.h6,
                        color = if (success) Color.Green else Color.Red,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = message ?: if (success) "Sudoku saved successfully!" else "An error occurred.",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Button(onClick = onDismiss) {
                        Text("OK")
                    }
                }
            }
        }
    }
}