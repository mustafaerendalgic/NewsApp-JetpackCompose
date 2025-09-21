package com.example.news.util

import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime


fun ParseFunction(utcString: String): String{
    val instant = Instant.parse(utcString)
    val zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
    val now = ZonedDateTime.now()
    val difference = Duration.between(zonedDateTime, now)
    return when{
        difference.toMinutes() < 2  -> "Just Now"
        difference.toMinutes() < 60 -> "${difference.toMinutes()} minutes ago"
        difference.toHours()   < 24 -> "${difference.toHours()} hours ago"
        else -> if(difference.toDays() > 1) "${difference.toDays()} days ago" else "1 day ago"
        }
}