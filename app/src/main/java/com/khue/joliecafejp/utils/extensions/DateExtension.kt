package com.khue.joliecafejp.utils.extensions

import com.khue.joliecafejp.utils.Constants.Companion.LOCAL_TIME_FORMAT
import java.text.SimpleDateFormat
import java.util.*

fun Date.formatTo(dateFormat: String = LOCAL_TIME_FORMAT, timeZone: TimeZone = TimeZone.getDefault()): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}



