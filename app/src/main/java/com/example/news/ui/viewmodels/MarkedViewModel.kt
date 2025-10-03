package com.example.news.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.news.data.entity.Articles
import com.example.news.data.repo.NewsRepo
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MarkedViewModel @Inject constructor(private val repo: NewsRepo) : ViewModel() {

    private val _markedList = MutableStateFlow<List<Articles>>(emptyList())
    val markedList: StateFlow<List<Articles>> = _markedList

    val db = FirebaseFirestore.getInstance()

    fun fetchFavoritesFromFirestore(userID: String){

        val userRef = db.collection("users").document(userID)

        userRef.get().addOnSuccessListener {

            if(it.exists()){

                val favoriteIDs = it.get("favorites") as List<String> ?: emptyList()

                if(favoriteIDs.isEmpty()){
                    _markedList.value = emptyList()
                }

                else{

                    db.collection("articles").whereIn(FieldPath.documentId(), favoriteIDs).get().addOnSuccessListener {

                        _markedList.value = it.toObjects(Articles::class.java)

                    }

                }

            }

        }

    }

}