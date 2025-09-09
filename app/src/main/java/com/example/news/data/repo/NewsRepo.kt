package com.example.news.data.repo

import android.util.Log
import com.example.news.data.entity.APICevap
import com.example.news.retrofit.NewsDAO
import java.util.Locale
import javax.inject.Inject
import com.example.news.BuildConfig

class NewsRepo(val dao: NewsDAO) {
    val locale: Locale = Locale.getDefault()
    val region = locale.country
    val apiKey = BuildConfig.API_KEY

    suspend fun fetchTheHeadlines(): APICevap{
        return dao.fetchTheHeadlines(region, apiKey)
    }

    suspend fun fetchByKeyword(keyword: String): APICevap{
        return dao.fetchEverything(keyword, apiKey)
    }

}