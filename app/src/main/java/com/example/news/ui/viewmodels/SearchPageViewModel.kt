package com.example.news.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.news.data.entity.Articles
import com.example.news.data.repo.NewsRepo
import com.example.news.util.hashUrl
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchPageViewModel @Inject constructor(val repo: NewsRepo): ViewModel() {

    private val _searchList = MutableStateFlow<List<Articles>>(emptyList())
    val searchList: StateFlow<List<Articles>> = _searchList

    private val _lastSearch = MutableStateFlow<List<Articles>>(emptyList())
    val lastSearchList: StateFlow<List<Articles>> = _lastSearch

    fun fetchByKeyword(keyword: String){
        try {
            viewModelScope.launch {
                if(keyword.trim() == "")
                    return@launch
                _searchList.value = repo.fetchByKeyword(keyword.trim()).articles
            }
        }
        catch (e: Exception){
            Log.e("everything fetch sıkıntısı", e.toString())
        }

    }

    fun saveTheLastSearch(uid: String?, article: Articles){
        uid?.let{
            val db = FirebaseFirestore.getInstance()
            val userRef = db.collection("users").document(uid)
            val articleID = hashUrl(article.url)
            val articlesRef = db.collection("articles").document(articleID)

            articlesRef.get().addOnSuccessListener {
                if(!it.exists()){
                    articlesRef.set(article)
                }
            }

            userRef.collection("lastSearch").document("hash").get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        userRef.collection("lastSearch").document("hash")
                            .update("hash", FieldValue.arrayUnion(articleID))
                            .addOnSuccessListener {
                                getTheLastSearch(uid)
                            }
                    } else {
                        userRef.collection("lastSearch").document("hash")
                            .set(mapOf("hash" to listOf(articleID)))
                            .addOnSuccessListener {
                                getTheLastSearch(uid)
                            }
                    }
                }
        }

    }

    fun getTheLastSearch(uid: String?){

        uid?.let {
            val db = FirebaseFirestore.getInstance()
            val lastSearchRef = db.collection("users").document(uid).collection("lastSearch").document("hash")
            val articlesRef = db.collection("articles")

            lastSearchRef.get()
                .addOnSuccessListener {
                    if(it.exists()){
                        val lastSearchIDs = it.get("hash") as? List<String> ?: emptyList()

                        articlesRef.get().addOnSuccessListener {
                            val articles = lastSearchIDs.reversed().mapNotNull { id ->
                                it.documents.find { it.id == id }?.toObject(Articles::class.java)
                            }

                            _lastSearch.value = articles

                        }

                    }
                    else
                        _lastSearch.value = emptyList()
                }

        }


    }

}