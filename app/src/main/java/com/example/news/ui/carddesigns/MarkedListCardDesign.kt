package com.example.news.ui.carddesigns

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.Timelapse
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.news.R
import com.example.news.data.entity.Articles
import com.example.news.data.entity.Source
import com.example.news.util.FetchTheLogo
import com.example.news.util.ParseFunction

@Composable
fun MarkedListCardDesign(article: Articles, onClick: () -> Unit, date: String){
    Column(modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .fillMaxWidth()
        .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Row(modifier = Modifier.padding(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically) {

            AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data(FetchTheLogo(article))
                .error(R.drawable.outline_ar_stickers_24)
                .fallback(R.drawable.outline_ar_stickers_24)
                .build(),
                contentDescription = "",
                modifier = Modifier.clip(CircleShape).size(40.dp))

            Text(text = article.source.name,
                modifier = Modifier,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.gabarito)),
                fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.weight(1f))

            Icon(Icons.Rounded.Timelapse, tint = colorResource(R.color.mavi).copy(alpha = 0.8f), contentDescription = "",
                modifier = Modifier.size(16.dp))

            Text(text = ParseFunction(article.publishedAt),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.gabarito)),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            )

        }

        Text(text = article.title,
            modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
            fontSize = 16.sp,
            lineHeight = 18.sp,
            maxLines = 2,
            color = MaterialTheme.colorScheme.onBackground,
            overflow = TextOverflow.Ellipsis,
            fontFamily = FontFamily(Font(R.font.gabarito)))

        AsyncImage(model = ImageRequest.Builder(LocalContext.current)
            .data(article.urlToImage)
            .error(R.drawable.outline_ar_stickers_24)
            .fallback(R.drawable.outline_ar_stickers_24)
            .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.padding(top = 16.dp).fillMaxWidth().height(300.dp).clip(RoundedCornerShape(8.dp)))

        Row(modifier = Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)) {

        Text(text = "Marked on: " + date,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.gabarito)),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(0.7f),
            overflow = TextOverflow.Ellipsis)

            Spacer(modifier = Modifier.weight(1f))

            Icon(Icons.Filled.Share,
                contentDescription = "",
                tint = Color.Black.copy(alpha = 0.7f),
                modifier = Modifier.size(20.dp))

            Icon(Icons.Filled.Bookmark, contentDescription = "", tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable{
                    onClick()
                })

        }

    }
}

@Composable
@Preview(showBackground = true)
fun MarkedListCardDesignPrev(){
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
    MarkedListCardDesign(dummyArticle, onClick = {}, "a")
}