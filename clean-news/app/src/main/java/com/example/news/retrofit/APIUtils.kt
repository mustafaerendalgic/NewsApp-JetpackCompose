package com.example.news.retrofit

class APIUtils{
    companion object{
        val baseURL = "https://newsapi.org/v2/"

        fun getTheNews(): NewsDAO{
            return RetrofitClient.getClient(baseURL).create(NewsDAO::class.java)
        }

    }
}