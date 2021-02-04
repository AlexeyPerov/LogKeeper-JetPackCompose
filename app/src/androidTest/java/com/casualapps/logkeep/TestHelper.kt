package com.casualapps.logkeep

import android.content.Context
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.lifecycle.SavedStateHandle
import com.casualapps.logkeep.ui.LogKeeperApp
import com.casualapps.logkeep.ui.common.NavigationViewModel

fun ComposeTestRule.launchLogKeeperApp(context: Context) {
    setContent {
        LogKeeperApp(
            TestAppContainer(context),
            remember { NavigationViewModel(SavedStateHandle()) }
        )
    }
}
