package com.casualapps.logkeep.data.logs.mock

import com.casualapps.logkeep.data.logs.LogsRepository
import com.casualapps.logkeep.model.LogCreationArguments
import com.casualapps.logkeep.model.LogEntity
import com.casualapps.logkeep.model.LogInfoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Exception
import com.casualapps.logkeep.utils.Result

@OptIn(ExperimentalCoroutinesApi::class)
class MockLogsRepository : LogsRepository {
    override suspend fun getProjects(): Result<Set<String>> {
        return withContext(Dispatchers.IO) {
            delay(800)
            Result.Success(projectsSource)
        }
    }

    override suspend fun getLogById(logId: String): Result<LogEntity> {
        return withContext(Dispatchers.IO) {
            delay(800)
            var result: LogEntity? = null;
            for (list in logsSource) {
                val log = list.value.find { it.id == logId }
                if (log != null) {
                    result = LogEntity(list.key, log,
                        generateLogContents(log.contentsId, 100))
                    break;
                }
            }

            if (result != null) {
                Result.Success(result)
            } else {
                Result.Error(Exception("Log not found"))
            }
        }
    }

    override suspend fun getLogInProjectById(project: String, logId: String): Result<LogEntity> {
        return withContext(Dispatchers.IO) {
            delay(800)
            val projectLogs = logsSource[project] ?: error("Unable to find project: $project");
            val log = projectLogs.find { it.id == logId }
            if (log == null) {
                Result.Error(Exception("Log not found"))
            } else {
                Result.Success(LogEntity(project, log,
                    generateLogContents(log.contentsId, 100)))
            }
        }
    }

    override suspend fun getLogsForProject(project: String): Result<List<LogInfoEntity>> {
        return withContext(Dispatchers.IO) {
            delay(800)
            Result.Success(logsSource[project] ?: emptyList())
        }
    }

    override suspend fun addLog(project: String, logData: LogCreationArguments) {
        TODO("Not yet implemented")
    }

    override suspend fun removeLog(project: String, logId: String) {
        TODO("Not yet implemented")
    }
}
