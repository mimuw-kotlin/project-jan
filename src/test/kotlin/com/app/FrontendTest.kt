package com.app

import androidx.compose.material.Button
import androidx.compose.material.Text
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.performClick


class FrontendTest {

    //Before each test clean and populate the database
    @BeforeEach
    fun setup() {
        populate()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun todo() = runComposeUiTest {
        setContent {
        }


    }
}

