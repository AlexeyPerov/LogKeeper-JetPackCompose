package com.casualapps.logkeep.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.casualapps.logkeep.LogKeeperApplication
import com.casualapps.logkeep.ui.common.NavigationViewModel

class MainActivity : AppCompatActivity() {
    private val navigationViewModel by viewModels<NavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as LogKeeperApplication).container
        setContent {
            LogKeeperApp(appContainer, navigationViewModel)
        }
    }

    override fun onBackPressed() {
        if (!navigationViewModel.onBack()) {
            super.onBackPressed()
        }
    }
}
