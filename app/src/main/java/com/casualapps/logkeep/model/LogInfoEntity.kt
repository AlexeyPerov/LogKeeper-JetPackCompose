package com.casualapps.logkeep.model

import java.util.*

data class LogInfoEntity(
    val id: String,
    val title: String,
    val author: String,
    val contentsId: String,
    val createdAt: Date
)