package com.example.news.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.news.ui.viewmodels.MainPageViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.news.data.entity.Articles
import com.example.news.data.entity.Source
import com.example.news.ui.carddesigns.HeadlinesDesign



@Composable
fun MainPage(){

    val viewModel: MainPageViewModel = hiltViewModel()
    val articleList by viewModel.listOfHeadlines.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchTheHeadlines()
    }

    MainPageUI(articleList = articleList)

}

@Composable
fun MainPageUI(articleList: List<Articles>){

    Column(modifier = Modifier.fillMaxSize()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
        )

        {
        Text(text = "Top Headlines",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
        )
        LazyRow(verticalAlignment = Alignment.Top){
            items(articleList) { article ->
                HeadlinesDesign(article)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun mainPreview(){

    val dummyArticles = listOf(
        Articles(
            source = Source(
                id = null,
                name = "CNBC"
            ),
            author = "Brian Evans",
            title = "Stock futures tick higher to start the week as traders look ahead to key inflation data: Live updates - CNBC",
            description = "Stock futures ticked higher on Monday as investors gear up for a data-heavy week that includes two closely watched readings on inflation.",
            url = "https://www.cnbc.com/2025/09/07/stock-market-today-live-updates.html",
            urlToImage = "https://image.cnbcfm.com/api/v1/image/108154805-1749068892466-NYSE_Traders-OB-20250604-CC-PRESS-10.jpg?v=1749069908&w=1920&h=1080",
            publishedAt = "2025-09-08T10:08:00Z",
            content = "Stock futures ticked higher on Monday as investors gear up for a data-heavy week that includes two closely watched readings on inflation."
        ),
        Articles(
            source = Source(
                id = null,
                name = "BBC News"
            ),
            author = null,
            title = "Norway's tight vote to decide whether to stick with Labour or turn right - BBC",
            description = "Norwegians are going to the polls, with domestic issues expected to be at the forefront of voters' minds.",
            url = "https://www.bbc.com/news/articles/c701382e994o",
            urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/49d2/live/62b61cd0-8c80-11f0-9cf6-cbf3e73ce2b9.jpg",
            publishedAt = "2025-09-08T06:47:17Z",
            content = "Norwegians are going to the polls in a tight race…"
        )
    )

    MainPageUI(dummyArticles)

}

