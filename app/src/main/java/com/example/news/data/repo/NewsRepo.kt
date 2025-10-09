package com.example.news.data.repo

import android.util.Log
import com.example.news.BuildConfig
import com.example.news.data.entity.APICevap
import com.example.news.retrofit.NewsDAO

class NewsRepo(val dao: NewsDAO) {
    val apiKey = BuildConfig.API_KEY

    suspend fun fetchTheHeadlines(): APICevap{
        Log.d("apicevap", "${dao.fetchTheHeadlines("us", apiKey)}")
        return dao.fetchTheHeadlines("us", apiKey)
    }

    suspend fun fetchByKeyword(keyword: String): APICevap{
        return dao.fetchEverything(keyword, apiKey)
    }

    suspend fun fetchTheHeadlinesByCategory(category: String): APICevap{
        Log.d("NewsRepo", "fetchTheHeadlinesByCategory: category=$category")
        return dao.fetchTheHeadlinesByCategory(category, country = "us", apiKey= apiKey)
    }

}