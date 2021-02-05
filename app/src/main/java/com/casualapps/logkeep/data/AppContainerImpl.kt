package com.casualapps.logkeep.data

import android.content.Context
import com.casualapps.logkeep.data.logs.LogsRepository
import com.casualapps.logkeep.data.logs.firebase.FirebaseLogsRepository

interface AppContainer {
    val logsRepository: LogsRepository
}

class AppContainerImpl(private val applicationContext: Context) : AppContainer {
    override val logsRepository: LogsRepository by lazy {
        FirebaseLogsRepository()
    }
}
