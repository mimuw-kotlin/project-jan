package com.app

import SudokuScreen
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FrontendTest {
    // Before each test clean and populate the database
    @BeforeEach
    fun setup() {
        populate()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testEditingNodes() =
        runComposeUiTest {
            setContent {
                SudokuScreen { }
            }

            mainClock.autoAdvance = false

            // Unpausing the game
            val pause = onNodeWithTag("pauseButton")
            pause.assertTextEquals("Unpause")
            pause.performClick()
            pause.assertTextEquals("Pause")

            // Checking if the top left node is equal to 5, it should be because of 'populate' function
            val upperLeftSudokuNode = onNodeWithTag("node 0 0")
            upperLeftSudokuNode.assertTextEquals("5")

            // changing the second node to number 1
            val secondUpperNode = onNodeWithTag("node 0 1")
            secondUpperNode.performClick()
            val number1 = onNodeWithTag("number 1")
            number1.performClick()
            secondUpperNode.assertTextEquals("1")
        }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testNotes() =
        runComposeUiTest {
            mainClock.autoAdvance = false

            setContent {
                SudokuScreen { }
            }

            val pause = onNodeWithTag("pauseButton")
            pause.performClick()

            // Enabling notes
            val notesButton = onNodeWithTag("notesButton")
            notesButton.assertExists()
            notesButton.assertTextEquals("Notes: inactive")
            notesButton.performClick()
            notesButton.assertTextEquals("Notes: active")

            // Editing notes
            val secondUpperNode = onNodeWithTag("node 0 1")
            secondUpperNode.performClick()
            val number1 = onNodeWithTag("number 1")
            number1.performClick()
            secondUpperNode.assertTextEquals("1")
            secondUpperNode.performClick()
            val number2 = onNodeWithTag("number 2")
            number2.performClick()
            // Notatki zawierają zarówno nr 1 jak i nr 2
            secondUpperNode.assertTextContains("1")
            secondUpperNode.assertTextContains("2")
        }
}
