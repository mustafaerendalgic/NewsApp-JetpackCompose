package com.example.news.data.repo

import android.util.Log
import com.example.news.data.entity.APICevap
import com.example.news.retrofit.NewsDAO
import java.util.Locale
import javax.inject.Inject
import com.example.news.BuildConfig

class NewsRepo(val dao: NewsDAO) {
    val apiKey = BuildConfig.API_KEY

    suspend fun fetchTheHeadlines(): APICevap{
        Log.d("apicevap", "${dao.fetchTheHeadlines("us", apiKey)}")
        return dao.fetchTheHeadlines("us", apiKey)
    }

    suspend fun fetchByKeyword(keyword: String): APICevap{
        return dao.fetchEverything(keyword, apiKey)
    }

}