package com.casualapps.logkeep.utils

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.casualapps.logkeep.ui.theme.LogKeeperTheme

@Composable
internal fun ThemedPreview(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    LogKeeperTheme(darkTheme = darkTheme) {
        Surface {
            content()
        }
    }
}
