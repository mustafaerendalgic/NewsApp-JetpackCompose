package com.example.news.ui.viewmodels

import android.util.Log
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


    private val _selectedCategory = MutableStateFlow("Sports")
    val selectedCategory: StateFlow<String> = _selectedCategory

    private val _lastSelectedCategory = MutableStateFlow("")
    val lastSelectedCategory: StateFlow<String> = _lastSelectedCategory

    fun selectCategory(cat: String){
        _selectedCategory.value = cat
        fetchTheHeadlinesByCategory(cat)
    }

    private val _listOfDetailArticles = MutableStateFlow<List<Articles>>(emptyList())
    val listOfDetailArticles: StateFlow<List<Articles>> = _listOfDetailArticles

    private val _listOfHeadlines = MutableStateFlow<List<Articles>>(emptyList())
    val listOfHeadlines: StateFlow<List<Articles>> = _listOfHeadlines

    private val _detailItem = MutableStateFlow<Articles?>(null)
    val detailItem: StateFlow<Articles?> = _detailItem

    private val _categoryItemList = MutableStateFlow<List<Articles>>(emptyList())
    val categoryItemList: StateFlow<List<Articles>> = _categoryItemList

    fun selectedArticle(articles: Articles){
        _detailItem.value = articles
    }

    fun fetchTheHeadlinesByCategory(category: String){
        viewModelScope.launch {
            Log.d("NewsRepo", "${repo.fetchTheHeadlinesByCategory(category).articles}")
                _categoryItemList.value = repo.fetchTheHeadlinesByCategory(category).articles
            Log.d("NewsRepo2", "${_categoryItemList}")
            }
    }


    fun fetchRelatedArticles(keyword: String){
        viewModelScope.launch{
            _listOfDetailArticles.value = repo.fetchTheHeadlinesByCategory(keyword).articles
        }
    }

    fun fetchTheHeadlines(){
        viewModelScope.launch {
            _listOfHeadlines.value = repo.fetchTheHeadlinesByCategory("general").articles
        }
    }

    fun setLastSelectedCategory(isFromCategory: Boolean){

        if(isFromCategory){
            _lastSelectedCategory.value = _selectedCategory.value.lowercase()
        }

        else{

            _lastSelectedCategory.value = "general"

        }

    }

}