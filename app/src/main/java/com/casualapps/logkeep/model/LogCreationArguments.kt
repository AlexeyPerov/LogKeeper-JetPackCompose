package com.casualapps.logkeep.model

import java.util.*

data class LogCreationArguments(
    val id: String,
    val title: String,
    val author: String,
    val contents: String,
    val createdAt: Date
)