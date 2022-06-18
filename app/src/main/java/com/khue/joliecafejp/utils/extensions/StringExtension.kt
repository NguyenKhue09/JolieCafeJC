package com.khue.joliecafejp.utils.extensions

import com.khue.joliecafejp.utils.Constants.Companion.UTC_TIME_FORMAT
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(dateFormat: String = UTC_TIME_FORMAT, timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date? {
    return try {
        val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
        parser.timeZone = timeZone
        parser.parse(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}