package com.example.news.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.news.R
import com.example.news.ui.carddesigns.SearchItemDesign
import com.example.news.ui.viewmodels.MainPageViewModel
import com.example.news.ui.viewmodels.SearchPageViewModel
import com.example.news.util.hashUrl
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SearchScreen(paddingValues: PaddingValues, searchViewModel: SearchPageViewModel, navController: NavController, mainPageViewModel: MainPageViewModel){
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    var search by remember { mutableStateOf("") }
    val searchList by searchViewModel.searchList.collectAsState()
    val lastSearch by searchViewModel.lastSearchList.collectAsState()
    searchViewModel.getTheLastSearch(uid)

    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        item {
            OutlinedTextField(
                value = search,
                onValueChange = {
                    search = it
                    searchViewModel.fetchByKeyword(search)
                },
                placeholder = { Text(text = "Search anything") },
                leadingIcon = {
                    Icon(
                        painterResource(R.drawable.magnifyingglass),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 4.dp)

            )
        }

        itemsIndexed(if(search == "") lastSearch else searchList) { index, item ->
            SearchItemDesign(item, onClick = {
                searchViewModel.saveTheLastSearch(uid, item)
                mainPageViewModel.selectedArticle(item)
                navController.navigate("details")})
        }

    }
}