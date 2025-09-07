package com.example.news.data.entity

data class APICevap(
    val status: String,
    val totalResults: Int,
    val articles: ArrayList<Articles>
)