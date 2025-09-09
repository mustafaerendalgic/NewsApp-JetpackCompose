package com.example.news.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.data.entity.Articles
import com.example.news.data.repo.NewsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MainPageViewModel@Inject constructor(private val repo: NewsRepo) : ViewModel() {

    private val _listOfArticles = MutableStateFlow<List<Articles>>(emptyList())
    val listOfArticles: StateFlow<List<Articles>> = _listOfArticles

    private val _listOfHeadlines = MutableStateFlow<List<Articles>>(emptyList())
    val listOfHeadlines: StateFlow<List<Articles>> = _listOfHeadlines

    fun fetchEverything(keyword: String){
        viewModelScope.launch{
            _listOfArticles.value = repo.fetchByKeyword(keyword).articles
        }
    }

    fun fetchTheHeadlines(){
        viewModelScope.launch {
            _listOfHeadlines.value = repo.fetchTheHeadlines().articles
        }
    }

}