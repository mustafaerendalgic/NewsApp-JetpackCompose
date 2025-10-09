package com.example.news.util

import java.security.MessageDigest

fun hashUrl(url: String): String {
    val md = MessageDigest.getInstance("SHA-256")
    return md.digest(url.toByteArray())
        .joinToString("") { "%02x".format(it) }
}


