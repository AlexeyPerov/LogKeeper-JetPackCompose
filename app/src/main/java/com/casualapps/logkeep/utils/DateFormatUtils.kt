package com.casualapps.logkeep.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(date: Date, pattern: String): String {
    val simpleDateFormat = SimpleDateFormat(pattern)
    return simpleDateFormat.format(date)
}