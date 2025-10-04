package com.example.news.util

import com.example.news.data.entity.Articles
import com.example.news.ui.viewmodels.MarkedViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.security.MessageDigest

fun hashUrl(url: String): String {
    val md = MessageDigest.getInstance("SHA-256")
    return md.digest(url.toByteArray())
        .joinToString("") { "%02x".format(it) }
}


