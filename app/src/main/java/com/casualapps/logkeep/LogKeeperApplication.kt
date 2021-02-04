package com.casualapps.logkeep

import android.app.Application
import com.casualapps.logkeep.data.AppContainer
import com.casualapps.logkeep.data.AppContainerImpl

class LogKeeperApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}
