package com.example.news.pages

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.news.R
import com.example.news.data.entity.Articles
import com.example.news.data.entity.Source
import com.example.news.ui.carddesigns.HeadlinesDesign
import org.intellij.lang.annotations.JdkConstants
import kotlin.math.absoluteValue


@Composable
fun MainPage(navController: NavController, viewModel: MainPageViewModel){

    val articleList by viewModel.listOfHeadlines.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchTheHeadlines()
    }

    MainPageUI(articleList = articleList, viewModel, navController)

}

@Composable
fun MainPageUI(articleList: List<Articles>, viewModel: MainPageViewModel, navController: NavController){

    val listState = rememberLazyListState()

    val conf = LocalConfiguration.current
    val width = min(conf.screenWidthDp.dp * 0.9f, 400f.dp)
    val sidePadding = if(width != 400f.dp )((conf.screenWidthDp.dp - width) / 2) else 16.dp

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        )

        {

        Row(modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 24.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically) {

            Text(text = "Top Headlines",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.gabarito)),
            )

            Spacer(Modifier.weight(1f))

            Text(text = "See All " + "(${articleList.size})",
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.gabarito)),
                color = Color(0xFF5F8DE3)
            )

        }

        LazyRow(verticalAlignment = Alignment.Top,
            state = listState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = listState),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = sidePadding)
        ){

            itemsIndexed(articleList) { index, article ->

                val visibleInfo = listState.layoutInfo.visibleItemsInfo
                val center = (listState.layoutInfo.viewportStartOffset +
                        listState.layoutInfo.viewportEndOffset) / 2
                var scale = 0.92f
                visibleInfo.find { it.index == index }?.let{iteminfo ->

                    val mostCentered = visibleInfo.minByOrNull {
                        val itemCenter = it.offset + it.size / 2
                        (center - itemCenter).absoluteValue
                    }
                    scale = if (mostCentered?.index == index) 1f else 0.92f

                }

                val animatedScale by animateFloatAsState(
                    targetValue = scale,
                    label = "scaleAnimation",
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = FastOutSlowInEasing
                    )
                )

                HeadlinesDesign(article,
                    modifier = Modifier
                        .width(width)
                        .graphicsLayer(scaleX = animatedScale, scaleY = animatedScale)
                        .clickable {
                            viewModel.selectedArticle(articleList[index])
                            navController.navigate("details")
                        })
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

    //MainPageUI(dummyArticles)

}

