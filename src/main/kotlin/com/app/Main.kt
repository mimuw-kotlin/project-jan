package com.app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.app.ui.screens.GameMenu
import kotlinx.coroutines.runBlocking

@Composable
@Preview
fun App() {
    GameMenu()
}

fun main() = application {
    populate()
    runBlocking {
        database.DatabaseConfig.init()
    }
    Window(onCloseRequest = ::exitApplication, title = "PuzzleVerse") {
        App()
    }
}
