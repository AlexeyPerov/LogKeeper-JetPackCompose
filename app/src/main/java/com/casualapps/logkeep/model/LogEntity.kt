package com.casualapps.logkeep.model

data class LogEntity(
    val project: String,
    val info: LogInfoEntity,
    val data: LogContentsEntity
)

