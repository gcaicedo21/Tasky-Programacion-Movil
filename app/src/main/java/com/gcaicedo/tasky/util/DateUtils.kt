package com.gcaicedo.tasky.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

private val displayFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("es", "CO"))

fun todayIso(): String = LocalDate.now().toString()

fun formatDate(isoDate: String): String {
    return runCatching {
        LocalDate.parse(isoDate).format(displayFormatter)
    }.getOrDefault(isoDate)
}

fun isoDateToUtcMillis(isoDate: String): Long {
    return LocalDate.parse(isoDate)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant()
        .toEpochMilli()
}

fun utcMillisToIsoDate(millis: Long): String {
    return Instant.ofEpochMilli(millis)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()
        .toString()
}
