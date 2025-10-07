package com.example.news.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Source
import androidx.compose.material.icons.rounded.Timelapse
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.news.data.entity.Articles
import com.example.news.ui.viewmodels.MainPageViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.news.BuildConfig
import com.example.news.R
import com.example.news.data.entity.Source
import com.example.news.ui.carddesigns.DetailHeadlinesDesignUI
import com.example.news.ui.viewmodels.MarkedViewModel
import com.example.news.util.FetchTheLogo
import com.example.news.util.ParseFunction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.net.URI

@Composable
fun DetailScreen(viewModel: MainPageViewModel,
                 paddingValues: PaddingValues,
                 navController: NavController,
                 markViewModel: MarkedViewModel){

    val article by viewModel.detailItem.collectAsState(initial = null)

    DetailsScreenUI(article, paddingValues, navController, viewModel, markViewModel)

}

@Composable
fun DetailsScreenUI(articles: Articles?,
                    paddingValues: PaddingValues,
                    navController: NavController,
                    viewModel: MainPageViewModel,
                    markViewModel: MarkedViewModel){

    articles?.let {

        val uriHandler = LocalUriHandler.current

        val logoURL = FetchTheLogo(articles)

        val relatedKeyword by viewModel.lastSelectedCategory.collectAsState()

        val markedList by markViewModel.markedList.collectAsState()
        val isMarked = markedList.any { it.articles.url == articles.url }
        val userID = FirebaseAuth.getInstance().currentUser?.uid

        LaunchedEffect(Unit) {
            viewModel.fetchRelatedArticles(relatedKeyword)
        }

        val filteredRelationList by viewModel.listOfDetailArticles.collectAsState()

        LazyColumn(modifier = Modifier.fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding(), bottom = 96.dp)
            .offset(y = -8.dp),
            ) {

            item{

                Box(modifier = Modifier.fillMaxWidth().height(400.dp)){

                    AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                        .data(articles.urlToImage)
                        .crossfade(true)
                        .placeholder(R.drawable.outline_ar_stickers_24)
                        .error(R.drawable.outline_ar_stickers_24)
                        .fallback(R.drawable.outline_ar_stickers_24)
                        .build(),
                        contentDescription = "detail picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(colors = listOf(Color.Transparent, Color.Black.copy(0.7f)),
                            startY = 100f))){}

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.4f)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Black.copy(alpha = 0.7f), Color.Transparent),
                                    startY = 0f
                                )
                            )
                    )

                    Column(modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp, bottom = 48.dp, end = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)) {




                        Text(text = articles.title.substringBeforeLast("-"),
                            fontSize = 24.sp,
                            fontFamily = FontFamily(Font(R.font.gabarito)),
                            lineHeight = 26.sp,
                            color = Color.White
                            //fontWeight = FontWeight.Bold,
                        )

                    }

                    Row(modifier = Modifier
                        .align(Alignment.TopStart)
                        .fillMaxWidth()
                        .padding(end = 16.dp, start = 8.dp, top = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(18.dp)) {

                        Icon(Icons.Filled.ArrowBackIosNew,
                            tint = Color.White, contentDescription = "",
                            modifier = Modifier.size(28.dp)
                                .clickable{
                                    navController.popBackStack()
                                })

                        Spacer(modifier = Modifier.weight(1f))

                        Icon(Icons.Filled.Share,
                            tint = Color.White,
                            contentDescription = "",
                            modifier = Modifier.size(28.dp))

                        Icon(if(!isMarked)Icons.Filled.BookmarkBorder else Icons.Filled.Bookmark,
                            tint = Color.White,
                            contentDescription = "",
                            modifier = Modifier.size(28.dp)
                                .clickable{
                                    if(!isMarked)
                                        markViewModel.saveArticleInFirestore(articles, userID)
                                    else
                                        markViewModel.deleteMarkedFromFirebase(userID, articles.url)
                                })
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxSize()
                    .offset(y = (-24).dp),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)

                ){
                    Column(modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)) {

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                            //.align(Alignment.TopStart)
                            ,
                            verticalAlignment = Alignment.CenterVertically) {

                            AsyncImage(model = logoURL,
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(32.dp)
                                    .clip(CircleShape))

                            Text(
                                text = articles.source.name,
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.gabarito)),
                                maxLines = 1,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(start = 4.dp)
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Icon(
                                Icons.Rounded.Timelapse,
                                tint = colorResource(R.color.mavi).copy(alpha = 0.8f),
                                contentDescription = "",
                                modifier = Modifier.size(18.dp)
                            )

                            Text(
                                text = ParseFunction(articles.publishedAt),
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                                fontFamily = FontFamily(Font(R.font.gabarito)),
                                lineHeight = 24.sp
                            )

                        }

                        val contentText = if(articles.content == null) articles.title.substringBeforeLast("-") else articles.content.substringBeforeLast("[")

                        val text = buildAnnotatedString {
                            append(contentText)
                            pushStringAnnotation(tag = "READ_MORE", annotation = "read_more")
                            withStyle(style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary
                            )) {
                                append(" Read More")
                            }

                            pop()
                        }

                        ClickableText(
                            text = text,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = FontFamily(Font(R.font.gabarito)),
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 18.sp
                            ),
                            onClick = { offset ->
                                text.getStringAnnotations("READ_MORE", offset, offset)
                                    .firstOrNull()?.let { annotation ->
                                        uriHandler.openUri(articles.url)
                                    }
                            }
                        )

                        articles.author?.let {

                            Row(modifier = Modifier.padding(end = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                                /*Spacer(modifier = Modifier.weight(1f))*/

                                Icon(Icons.Filled.Newspaper,
                                    tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                                    contentDescription = "")

                                Text(text = articles.author,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily(Font(R.font.gabarito)))
                            }

                        }



                        Text(text = "Like This",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 24.sp,
                            fontFamily = FontFamily(Font(R.font.gabarito)),
                            modifier = Modifier.padding())

                        val filteredList = filteredRelationList

                        LazyRow(modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(24.dp),
                        ) {

                            itemsIndexed(filteredList.filter { it.urlToImage != articles.urlToImage }) { index, article ->
                                val likeThisIsMarked = markedList.any { it.articles.url == article.url }
                                DetailHeadlinesDesignUI(article, navController = navController, viewModel = viewModel, isMarked = likeThisIsMarked, onClick = {
                                    if(likeThisIsMarked){
                                        markViewModel.deleteMarkedFromFirebase(userID = userID, article.url)
                                    }
                                    else{
                                        markViewModel.saveArticleInFirestore(article, userID)
                                    }
                                })

                            }

                        }

                    }
                }
            }

        }
    }
}



