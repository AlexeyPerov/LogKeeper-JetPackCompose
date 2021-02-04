package com.casualapps.logkeep

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSubstring
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
class LogKeeperUiTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.launchLogKeeperApp(InstrumentationRegistry.getInstrumentation().targetContext)
    }
    @Test
    fun app_launches() {
        composeTestRule.onNodeWithText("Log Keeper").assertIsDisplayed()
    }

    @Test
    fun app_opensLogContents() {
        composeTestRule.onAllNodes(hasSubstring("Test Log Report Name"))[0].performClick()
        composeTestRule.onAllNodes(hasSubstring("test contents"))[0].assertIsDisplayed()
    }
}
