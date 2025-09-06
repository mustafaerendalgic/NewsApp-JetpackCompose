package com.example.news.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsDAO{

    @GET("everything/")
    suspend fun fetchEverything(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    )

    @GET("top-headlines/")
    suspend fun fetchTheHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    )

    @GET("top-headlines/")
    suspend fun fetchTheSources(
        @Query("sources") country: String,
        @Query("apiKey") apiKey: String
    )

}