package com.example.news.util

import com.example.news.BuildConfig
import com.example.news.data.entity.Articles
import java.net.URI

fun FetchTheLogo(articles: Articles): String{

    val domain = URI.create(articles.url).host
    val query = domain.removePrefix("www.")
    val logoToken = BuildConfig.API_KEY_LOGO
    return "https://img.logo.dev/$domain?token=$logoToken&retina=true"

}