package com.example.news.di

import com.example.news.data.entity.APICevap
import com.example.news.data.repo.NewsRepo
import com.example.news.retrofit.APIUtils
import com.example.news.retrofit.NewsDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class Module {
    @Provides
    @Singleton
    fun provideDao(): NewsDAO{
        return APIUtils.getTheNews()
    }
    @Provides
    @Singleton
    fun provideRepo(dao: NewsDAO): NewsRepo{
        return NewsRepo(dao)
    }
}