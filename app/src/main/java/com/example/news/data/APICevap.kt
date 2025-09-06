package com.example.news.data

data class APICevap(
    val status: String,
    val totalResults: Int,
    val articles: ArrayList<Articles>
)