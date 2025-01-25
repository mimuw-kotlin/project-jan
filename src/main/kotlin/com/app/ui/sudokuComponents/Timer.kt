package com.app.ui.sudokuComponents

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// Timer component with pause functionality.
@Composable
fun Timer(isPaused: Boolean, currentTime: Long, updateTime: (Long) -> Unit) {
    var lastSystemTime by remember{ mutableStateOf(System.currentTimeMillis()) }

    LaunchedEffect(isPaused) {
        if(!isPaused){
            lastSystemTime = System.currentTimeMillis()
            delay(10)
            val delta = System.currentTimeMillis() - lastSystemTime
            updateTime(delta)
            lastSystemTime = System.currentTimeMillis()
        }
    }
    LaunchedEffect(currentTime){
        if(!isPaused){
            val delta = System.currentTimeMillis() - lastSystemTime
            updateTime(delta)
            lastSystemTime = System.currentTimeMillis()
            delay(10)
        }
    }

    Button(onClick = {}, Modifier.padding(start = 16.dp, bottom = 8.dp), enabled = false, colors = ButtonDefaults.buttonColors(
        disabledBackgroundColor = Color(0xFFC5705D), // Same as enabled background
        disabledContentColor = Color.White
    )){
        Text(
            DisplayTime(currentTime),
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.width(200.dp)
        )
    }
}