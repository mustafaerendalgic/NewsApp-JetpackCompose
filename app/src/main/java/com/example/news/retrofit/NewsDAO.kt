package com.example.news.retrofit

import com.example.news.data.entity.APICevap
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsDAO{

    @GET("everything/")
    suspend fun fetchEverything(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): APICevap

    @GET("top-headlines/")
    suspend fun fetchTheHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): APICevap

    @GET("top-headlines/")
    suspend fun fetchTheHeadlinesByCategory(
        @Query("category") category: String,
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): APICevap

}