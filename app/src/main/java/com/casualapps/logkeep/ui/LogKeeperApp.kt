package com.casualapps.logkeep.ui

import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.casualapps.logkeep.data.AppContainer
import com.casualapps.logkeep.data.logs.LogsRepository
import com.casualapps.logkeep.ui.article.LogContentScreen
import com.casualapps.logkeep.ui.article.LogScreenViewModel
import com.casualapps.logkeep.ui.common.NavigationViewModel
import com.casualapps.logkeep.ui.common.Screen
import com.casualapps.logkeep.ui.home.HomeScreen
import com.casualapps.logkeep.ui.home.HomeScreenViewModel
import com.casualapps.logkeep.ui.theme.LogKeeperTheme

@Composable
fun LogKeeperApp(
    appContainer: AppContainer,
    navigationViewModel: NavigationViewModel
) {
    LogKeeperTheme {
        AppContent(
            navigationViewModel = navigationViewModel,
            logsRepository = appContainer.logsRepository
        )
    }
}

@Composable
private fun AppContent(
    navigationViewModel: NavigationViewModel,
    logsRepository: LogsRepository
) {
    Crossfade(navigationViewModel.currentScreen) { screen ->
        Surface(color = MaterialTheme.colors.background) {
            when (screen) {
                is Screen.Home -> HomeScreen(
                    navigateTo = navigationViewModel::navigateTo,
                    viewModel = remember { HomeScreenViewModel(logsRepository) }
                )
                is Screen.LogContents -> LogContentScreen(
                    viewModel = LogScreenViewModel(screen.logId, screen.project, logsRepository),
                    onBack = { navigationViewModel.onBack() }
                )
            }
        }
    }
}
