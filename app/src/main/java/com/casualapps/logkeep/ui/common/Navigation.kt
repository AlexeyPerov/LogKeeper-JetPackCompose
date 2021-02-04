package com.casualapps.logkeep.ui.common

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.casualapps.logkeep.ui.common.Screen.LogContents
import com.casualapps.logkeep.ui.common.Screen.Home
import com.casualapps.logkeep.ui.common.ScreenName.LOG_CONTENTS
import com.casualapps.logkeep.ui.common.ScreenName.HOME
import com.casualapps.logkeep.utils.getMutableStateOf

private const val ARG_SCREEN = "screen_type"
private const val ARG_NAME = "screen_name"
private const val ARG_LOG = "logId"
private const val ARG_PROJECT = "project"

enum class ScreenName { HOME, LOG_CONTENTS }

sealed class Screen(val id: ScreenName) {
    object Home : Screen(HOME)
    data class LogContents(val project: String, val logId: String) : Screen(LOG_CONTENTS)
}

private fun Screen.toBundle(): Bundle {
    return bundleOf(ARG_NAME to id.name).also {
        if (this is LogContents) {
            it.putString(ARG_LOG, logId)
            it.putString(ARG_PROJECT, project)
        }
    }
}

private fun Bundle.toScreen(): Screen {
    return when (ScreenName.valueOf(getStringOrThrow(ARG_NAME))) {
        HOME -> Home
        LOG_CONTENTS -> {
            val logId = getStringOrThrow(ARG_LOG)
            val project = getStringOrThrow(ARG_PROJECT)
            LogContents(project, logId)
        }
    }
}

private fun Bundle.getStringOrThrow(key: String) =
    requireNotNull(getString(key)) { "Missing key '$key' in $this" }

class NavigationViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    var currentScreen: Screen by savedStateHandle.getMutableStateOf<Screen>(
        key = ARG_SCREEN,
        default = Home,
        save = { it.toBundle() },
        restore = { it.toScreen() }
    )
        private set

    @MainThread
    fun onBack(): Boolean {
        val wasHandled = currentScreen != Home
        currentScreen = Home
        return wasHandled
    }

    @MainThread
    fun navigateTo(screen: Screen) {
        currentScreen = screen
    }
}
