package com.example.news.ui.carddesigns

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.news.data.entity.Articles
import com.example.news.data.entity.Source


@Composable
fun HeadlinesDesign(article: Articles){

    val conf = LocalConfiguration.current
    val width = conf.screenWidthDp.dp * 9/10

    Card(
        modifier = Modifier
            .width(width)
        .padding(PaddingValues(8.dp)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)) {

        Box(modifier = Modifier.fillMaxWidth()
            .aspectRatio(16f/9f)){

            AsyncImage(
                model = article.urlToImage,
                contentDescription = article.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 100.0f
                        )
                    )
            )

            Text(text = article.title,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(PaddingValues(16.dp)))

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeadlinesDesignPrev(){
    val dummyArticle = Articles(
        source = Source(id = null, name = "CNBC"),
        author = "Brian Evans",
        title = "Stock futures tick higher...",
        description = "Stock futures ticked higher on Monday...",
        url = "https://cnbc.com",
        urlToImage = "https://image.cnbcfm.com/api/v1/image/108154805-1749068892466-NYSE_Traders-OB-20250604-CC-PRESS-10.jpg?v=1749069908&w=1920&h=1080",
        publishedAt = "2025-09-08",
        content = "Some content..."
    )
    HeadlinesDesign(dummyArticle)
}