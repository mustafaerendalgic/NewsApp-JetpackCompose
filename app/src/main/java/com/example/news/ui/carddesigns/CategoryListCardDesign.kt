package com.example.news.ui.carddesigns

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.news.R
import com.example.news.data.entity.Articles
import com.example.news.data.entity.Source

@Composable
fun CategoryListCardDesign(article: Articles){
    Card (modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(16f/9f),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
        ){
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)){

            AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data(article.urlToImage)
                .error(R.drawable.outline_ar_stickers_24)
                .fallback(R.drawable.outline_ar_stickers_24)
                .placeholder(R.drawable.outline_ar_stickers_24).build(),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(3f/4f)
                    .clip(shape = RoundedCornerShape(8.dp))
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardPrev(){
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
    CategoryListCardDesign(dummyArticle)
}