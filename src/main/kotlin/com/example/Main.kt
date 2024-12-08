package com.example

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.ui.screens.GameMenu

@Composable
@Preview
fun App() {
    GameMenu()
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}