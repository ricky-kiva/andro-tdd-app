package com.dicoding.newsapp.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.TimeZone

// use `coreLibraryDesugaringEnabled` in `build.gradle` to suppress Java 8 feature (Intent & DateTimeFormatter)
object DateFormatter {
    fun formatDate(currentDate: String, targetTimeZone: String): String {
        val instant = Instant.parse(currentDate)
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy | HH:mm")
            .withZone(ZoneId.of(targetTimeZone))
        return formatter.format(instant)
    }
}