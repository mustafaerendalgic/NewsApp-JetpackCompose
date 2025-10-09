package com.example.news.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.news.data.entity.Articles
import com.example.news.data.entity.ArticlesWithTime
import com.example.news.data.repo.NewsRepo
import com.example.news.util.hashUrl
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MarkedViewModel @Inject constructor(private val repo: NewsRepo) : ViewModel() {

    private val _markedList = MutableStateFlow<List<ArticlesWithTime>>(emptyList())
    val markedList: StateFlow<List<ArticlesWithTime>> = _markedList

    private val _markedListWithTime = MutableStateFlow<List<Date?>>(emptyList())
    val markedListWithTime: StateFlow<List<Date?>> = _markedListWithTime
    val db = FirebaseFirestore.getInstance()

    fun fetchFavoritesFromFirestore(userID: String?){

        userID?.let {

            val userRef = db.collection("users").document(userID)

            val favoritesRef = userRef.collection("favorites").orderBy("time", Query.Direction.DESCENDING)

            favoritesRef.get().addOnSuccessListener {

                if(it.isEmpty){
                    _markedList.value = emptyList()
                    _markedListWithTime.value = emptyList()
                    return@addOnSuccessListener
                }

                val favoriteIDs = it.documents.map { it.id }

                _markedListWithTime.value = it.documents.map {
                    it.getTimestamp("time")?.toDate() }

                db.collection("articles").whereIn(FieldPath.documentId(), favoriteIDs).get().addOnSuccessListener {

                    val articles = it.toObjects(Articles::class.java)

                    val ordered = favoriteIDs.map { id ->
                        articles.find { hashUrl(it.url) == id }
                    }

                    _markedList.value = ordered.mapIndexed { index, item ->
                        ArticlesWithTime(item!!, _markedListWithTime.value[index])
                    }

                }

            }

        }

    }


    fun saveArticleInFirestore(article: Articles, userID: String?){

        userID?.let {

            val db = FirebaseFirestore.getInstance()
            val articleID = hashUrl(article.url)
            val markObject = mapOf("hash" to articleID,
                "time" to FieldValue.serverTimestamp())

            val articleRef = db.collection("articles").document(articleID)

            articleRef.get().addOnSuccessListener {
                if(!it.exists()){
                    articleRef.set(article)
                }
            }

            val userRef = db.collection("users").document(userID)

            userRef.collection("favorites").document(articleID).set(markObject, SetOptions.merge()).addOnSuccessListener {
                fetchFavoritesFromFirestore(userID)
            }

        }

    }

    fun deleteMarkedFromFirebase(userID: String?, url: String){

        userID?.let {

            val db = FirebaseFirestore.getInstance()

            val userRef = db.collection("users").document(userID)

            userRef.collection("favorites").document(hashUrl(url)).delete().addOnSuccessListener {
                fetchFavoritesFromFirestore(userID)
            }

        }

    }

}