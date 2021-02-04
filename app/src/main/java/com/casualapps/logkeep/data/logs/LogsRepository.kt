package com.casualapps.logkeep.data.logs

import com.casualapps.logkeep.model.LogCreationArguments
import com.casualapps.logkeep.model.LogEntity
import com.casualapps.logkeep.model.LogInfoEntity
import com.casualapps.logkeep.utils.Result

interface LogsRepository {
    suspend fun getProjects(): Result<Set<String>>

    suspend fun getLogsForProject(project: String): Result<List<LogInfoEntity>>

    suspend fun addLog(project: String, logData: LogCreationArguments)
    suspend fun removeLog(project: String, logId: String)

    suspend fun getLogById(logId: String): Result<LogEntity>
    suspend fun getLogInProjectById(project: String, logId: String): Result<LogEntity>
}
