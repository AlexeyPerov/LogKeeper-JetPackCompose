package com.casualapps.logkeep

import android.content.Context
import com.casualapps.logkeep.data.AppContainer
import com.casualapps.logkeep.data.logs.LogsRepository
import com.casualapps.logkeep.data.logs.mock.MockLogsRepository

class TestAppContainer(private val context: Context) : AppContainer {

    override val logsRepository: LogsRepository by lazy {
        MockLogsRepository()
    }
}
