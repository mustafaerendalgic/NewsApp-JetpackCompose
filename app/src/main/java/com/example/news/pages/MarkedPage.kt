package com.example.news.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.news.ui.viewmodels.MainPageViewModel
import com.example.news.ui.viewmodels.MarkedViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.news.R
import com.example.news.ui.carddesigns.MarkedListCardDesign
import com.example.news.util.FormatTimestamps
import com.example.news.util.ParseFunction
import com.google.firebase.auth.FirebaseAuth
import java.util.Date


@Composable
fun MarkedScreen(paddingValues: PaddingValues,
                 markViewModel: MarkedViewModel){
    val markedList by markViewModel.markedList.collectAsState()
    var filteredlist by remember { mutableStateOf(markedList) }

    LaunchedEffect(markedList) {
        filteredlist = markedList
    }

    val uid = FirebaseAuth.getInstance().currentUser?.uid

    var search by remember { mutableStateOf("") }

    LazyColumn(modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {

        item {
            Box(modifier = Modifier
                .padding(horizontal = 16.dp)
                .height(60.dp)
                .fillMaxWidth()) {

                OutlinedTextField(
                    value = search,
                    onValueChange = {
                        search = it
                        filteredlist =  markedList.filter { it.articles.title.lowercase().contains(search.lowercase()) }
                    },
                    placeholder = { Text(text = "Search in your marked articles") },
                    leadingIcon = {
                        Icon(
                            painterResource(R.drawable.magnifyingglass),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

            }
        }

        itemsIndexed(filteredlist) { index, item ->
            val date = item.time
            MarkedListCardDesign(item.articles, onClick = {
                markViewModel.deleteMarkedFromFirebase(uid, item.articles.url)
            },
                FormatTimestamps(date)
            )
            Divider(color = Color(0x7d7d7d).copy(alpha = 0.5f), thickness = (0.5).dp, modifier = Modifier.padding(top = 8.dp))
        }

    }

}