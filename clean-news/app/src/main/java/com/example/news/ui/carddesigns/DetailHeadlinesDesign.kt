package com.example.news.ui.carddesigns

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.rounded.Timelapse
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.news.R
import com.example.news.data.entity.Articles
import com.example.news.data.entity.Source
import com.example.news.ui.viewmodels.MainPageViewModel
import com.example.news.util.ParseFunction

@Composable
fun DetailHeadlinesDesignUI(articles: Articles, navController: NavController, viewModel: MainPageViewModel){

    Card(modifier = Modifier
        .width(300.dp)
        .aspectRatio(16f/9f)
        .clickable{
            viewModel.selectedArticle(articles)
        },
        shape = RoundedCornerShape(16.dp)
    ) {

        Box(modifier = Modifier.fillMaxSize()){

            AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data(articles.urlToImage)
                .placeholder(R.drawable.outline_ar_stickers_24)
                .error(R.drawable.outline_ar_stickers_24)
                .fallback(R.drawable.outline_ar_stickers_24)
                .build(),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(modifier = Modifier
                .fillMaxHeight(0.5f).fillMaxWidth()
                .background(Brush.verticalGradient(listOf(Color.Black.copy(alpha = 0.7f), Color.Transparent), startY = 0f))){}

            Box(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)), startY = 100f))){}

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.Timelapse, tint = colorResource(R.color.mavi), contentDescription = "",
                    modifier = Modifier.size(20.dp))

                Text(text = ParseFunction(articles.publishedAt),
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.gabarito)),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    color = Color.White
                )

                Spacer(Modifier.weight(1f))

                Icon(Icons.Outlined.BookmarkBorder, contentDescription = "Mark",
                    modifier = Modifier,
                    tint = colorResource(R.color.white),
                )

            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(PaddingValues(16.dp)),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {

                Text(text = articles.title,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.gabarito)),
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2)

            }

        }

    }

}
