package com.example.news.util

import android.util.Log
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun FormatTimestamps(date: Date?) : String{
    try {
        val utc = ZoneId.systemDefault()
        val instant = ZonedDateTime.ofInstant(date?.toInstant(), utc)
        val formatter = DateTimeFormatter.ofPattern("dd MMM HH:mm", Locale.forLanguageTag("en"))
        return instant.format(formatter)
    }
    catch (e: Exception){
        Log.e("patata", e.toString())
        return "Bi sıkıntı varrrrrrrrrrrrrrrr"
    }

}