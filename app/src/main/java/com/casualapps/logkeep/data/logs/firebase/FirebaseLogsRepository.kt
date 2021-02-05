package com.casualapps.logkeep.data.logs.firebase

import com.casualapps.logkeep.data.logs.LogsRepository
import com.casualapps.logkeep.model.LogContentsEntity
import com.casualapps.logkeep.model.LogCreationArguments
import com.casualapps.logkeep.model.LogEntity
import com.casualapps.logkeep.model.LogInfoEntity
import com.casualapps.logkeep.utils.Result
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseLogsRepository() : LogsRepository {
    override suspend fun getProjects(): Result<Set<String>> {
        val db = FirebaseFirestore.getInstance()
        val querySnapshot = db.collection("projects").get().await();

        var result: MutableSet<String> = mutableSetOf()

        for (document in querySnapshot) {
            val name = document.data["name"].toString()
            result.add(name)
        }

        return Result.Success(result)
    }

    override suspend fun getLogById(logId: String): Result<LogEntity> {
        TODO("Not implemented")
    }

    override suspend fun getLogInProjectById(project: String, logId: String): Result<LogEntity> {
        val db = FirebaseFirestore.getInstance()
        val collectionName = getProjectCollectionName(project)

        val logDocument = db.collection(collectionName).document(logId).get().await()
        val logData = logDocument.data!!

        val contentsId = logData["contentsId"].toString()

        val logContentsDocument = db.collection("logs").document(contentsId).get().await()
        val logContentsData = logContentsDocument.data!!

        val timestamp = logData["createdAt"] as com.google.firebase.Timestamp
        val date = timestamp.toDate()

        return Result.Success(LogEntity(project = project,
            info = LogInfoEntity(
            id = logId,
            title = logData["title"].toString(),
            author = logData["author"].toString(),
            createdAt = date,
            contentsId = contentsId
        ),
            data = LogContentsEntity(
                id = contentsId,
                contents = logContentsData["contents"].toString()
        )))
    }

    override suspend fun getLogsForProject(project: String): Result<List<LogInfoEntity>> {
        val db = FirebaseFirestore.getInstance()
        val collectionName = getProjectCollectionName(project)

        val logDocuments = db.collection(collectionName).get().await()

        var result: MutableList<LogInfoEntity> = mutableListOf()

        for (logDocument in logDocuments) {
            val logData = logDocument.data!!
            val timestamp = logData["createdAt"] as com.google.firebase.Timestamp
            val date = timestamp.toDate()
            result.add(
                LogInfoEntity(
                    id = logDocument.id,
                    title = logData["title"].toString(),
                    author = logData["author"].toString(),
                    createdAt = date,
                    contentsId = logData["contentsId"].toString()
                ))
        }

        return Result.Success(result)
    }

    override suspend fun addLog(project: String, logData: LogCreationArguments) {
        TODO("Not implemented")
    }

    override suspend fun removeLog(project: String, logId: String) {
        TODO("Not implemented")
    }

    private fun getProjectCollectionName(project: String): String {
        val projectPrefix = project.replace(' ', '_').toLowerCase()
        return projectPrefix + "_logs"
    }
}