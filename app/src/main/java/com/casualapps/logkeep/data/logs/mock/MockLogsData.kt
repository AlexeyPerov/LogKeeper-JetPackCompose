package com.casualapps.logkeep.data.logs.mock

import com.casualapps.logkeep.model.LogContentsEntity
import com.casualapps.logkeep.model.LogEntity
import com.casualapps.logkeep.model.LogInfoEntity
import java.util.*

val projectsSource: Set<String> = setOf("AoC Dev", "AoC Prod", "AoC Pre Prod")

val log1 = LogInfoEntity(
    id = "11",
    title = "Test Log Report 1",
    author = "Developer",
    contentsId = "111",
    createdAt = Date()
)

val log2 = LogInfoEntity(
    id = "22",
    title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit,",
    author = "Developer",
    contentsId = "111",
    createdAt = Date()
)

val log3 = LogInfoEntity(
    id = "33",
    title = "A condimentum vitae sapien pellentesque. " +
            "Dictum varius duis at consectetur lorem donec massa sapien",
    author = "Developer Developer",
    contentsId = "111",
    createdAt = Date()
)

val log4 = LogInfoEntity(
    id = "44",
    title = "Lectus magna fringilla urna porttitor rhoncus",
    author = "Developer",
    contentsId = "111",
    createdAt = Date()
)

val log5 = LogInfoEntity(
    id = "55",
    title = "Netus et malesuada fames ac turpis egestas maecenas pharetra convallis",
    author = "Developer Developer Developer",
    contentsId = "111",
    createdAt = Date()
)

val testLog = LogEntity(
    project = "Test Project",
    info = log5,
    data = LogContentsEntity("111", "Lorem ipsum dolor sit amet, " +
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et " +
            "dolore magna aliqua. " +
            "Morbi tristique senectus et netus et malesuada fames. Pulvinar mattis nunc sed " +
            "blandit libero. " +
            "A condimentum vitae sapien pellentesque. " +
            "Dictum varius duis at consectetur lorem donec massa sapien.")
)

fun generateLogContents(id: String, linesCount: Int): LogContentsEntity {
    var contents = "Test contents"
    for (i in 1..linesCount) {
        contents += "\n$i:" + generateRandomString(45);
    }

    return LogContentsEntity(id, contents)
}

fun generateRandomString(length: Int): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9') + ' ' + ';' + ':'
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

var logsSource: Map<String, List<LogInfoEntity>> = mutableMapOf(
    "AoC Dev" to listOf(
        log1,
        log2,
        log3,
    ),
    "AoC Prod" to listOf(
        log4,
        log5,
        log1.copy(id = "log6"),
        log2.copy(id = "log7"),
        log3.copy(id = "log8"),
        log1.copy(id = "log9"),
        log2.copy(id = "log10"),
        log3.copy(id = "log11"),
    ),
    "AoC Pre Prod" to listOf(
        log4.copy(id = "log9"),
        log5.copy(id = "log10")
    )
)