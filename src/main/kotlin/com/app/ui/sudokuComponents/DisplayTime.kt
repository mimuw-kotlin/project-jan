package com.app.ui.sudokuComponents

import androidx.compose.runtime.Composable

// Additional function to display time from milliseconds.
@Composable
fun DisplayTime(currentTime: Long): String{
    val hours = (currentTime / 3600000L) % 24
    val minutes = (currentTime / 60000L) % 60
    val seconds = (currentTime / 1000L) % 60
    val milliseconds = currentTime % 1000L

    return "Time: %02d:%02d:%02d.%03d".format(hours, minutes, seconds, milliseconds)
}
