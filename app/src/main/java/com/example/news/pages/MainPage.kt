package com.example.news.pages

import android.os.Bundle
import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.news.R
import com.example.news.data.entity.Articles
import com.example.news.data.entity.Source
import com.example.news.ui.carddesigns.CardDesignForCategories
import com.example.news.ui.carddesigns.CategoryListCardDesign
import com.example.news.ui.carddesigns.HeadlinesDesign

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.analytics.logEvent

import org.intellij.lang.annotations.JdkConstants
import kotlin.math.absoluteValue


@Composable
fun MainPage(
    navController: NavController,
    viewModel: MainPageViewModel,
    paddingValues: PaddingValues
) {

    val articleList by viewModel.listOfHeadlines.collectAsState(initial = emptyList())
    val articleListByCategory by viewModel.categoryItemList.collectAsState(initial = emptyList())
    val category by viewModel.selectedCategory.collectAsState()

    Log.d("NewsRepo1", "${articleListByCategory}")

    LaunchedEffect(Unit) {
        viewModel.fetchTheHeadlines()
        viewModel.fetchTheHeadlinesByCategory(category)
    }

    MainPageUI(articleList = articleList, viewModel, navController, paddingValues, articleListByCategory)

}

@Composable
fun MainPageUI(
    articleList: List<Articles>,
    viewModel: MainPageViewModel,
    navController: NavController,
    padding: PaddingValues,
    articleListByCategory: List<Articles>
) {

    val listState = rememberLazyListState()
    val categoryListState = rememberLazyListState()

    val conf = LocalConfiguration.current
    val width = min(conf.screenWidthDp.dp * 0.9f, 400.dp)
    val sidePadding = if (width != 400f.dp) ((conf.screenWidthDp.dp - width) / 2) else 16.dp

    val screenWidth = conf.screenWidthDp
    val iconWidth = 56
    var paddingForCategories = 0.dp
    var numberOfIconsVisible = (screenWidth / (iconWidth))

    paddingForCategories = ((screenWidth - (numberOfIconsVisible * iconWidth)) / (numberOfIconsVisible + 1)).dp

    while(paddingForCategories <= 24.dp){
        numberOfIconsVisible -= 1
        paddingForCategories = ((screenWidth - (numberOfIconsVisible * iconWidth)) / (numberOfIconsVisible + 1)).dp
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = padding.calculateTopPadding(), bottom = 96.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    )
    {

        item {
            Row(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Top Headlines",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.gabarito)),
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = "See All " + "(${articleList.size})",
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.gabarito)),
                    color = Color(0xFF5F8DE3)
                )

            }
        }

        item {

            LazyRow(
                verticalAlignment = Alignment.Top,
                state = listState,
                flingBehavior = rememberSnapFlingBehavior(lazyListState = listState),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = sidePadding)
            ) {

                itemsIndexed(articleList) { index, article ->

                    val visibleInfo = listState.layoutInfo.visibleItemsInfo
                    val center = (listState.layoutInfo.viewportStartOffset +
                            listState.layoutInfo.viewportEndOffset) / 2
                    var scale = 0.92f
                    visibleInfo.find { it.index == index }?.let { iteminfo ->

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

                    HeadlinesDesign(
                        article,
                        modifier = Modifier
                            .width(width)
                            .graphicsLayer(scaleX = animatedScale, scaleY = animatedScale)
                            .clickable {
                                viewModel.selectedArticle(articleList[index])
                                navController.navigate("details")
                                viewModel.setLastSelectedCategory(false)
                            })
                }
            }
        }

        item {

            val categories = listOf(
                R.drawable.running to "Sports",
                R.drawable.atom to "Science",
                R.drawable.projectmanagement to "Tech",
                R.drawable.video to "Media",
                R.drawable.briefcase to "Business",
                R.drawable.protection to "Health"
            )

            val categ by viewModel.selectedCategory.collectAsState()

            LazyRow(
                modifier = Modifier
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(paddingForCategories),
                state = categoryListState,
                flingBehavior = rememberSnapFlingBehavior(categoryListState)
            ) {

                item {
                    Spacer(modifier = Modifier.width(0.dp))
                }

                itemsIndexed(categories) { index, item ->

                    var label = item.second

                    if(item.second.lowercase() == "media")
                        label = "Entertainment"
                    else if(item.second.lowercase() == "tech")
                        label = "Technology"

                    CardDesignForCategories(item.first, item.second, isSelected = label == categ, onClick = {

                        viewModel.selectCategory(label)

                    })
                }

                item {
                    Spacer(modifier = Modifier.width(0.dp))
                }

            }

        }

        itemsIndexed(articleListByCategory) { index, item ->

            CategoryListCardDesign(item, onClick = {
                viewModel.setLastSelectedCategory(true)
                viewModel.selectedArticle(item)
                navController.navigate("details")
            })

        }

    }

}
