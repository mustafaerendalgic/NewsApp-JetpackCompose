package com.example.news.ui.carddesigns

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.news.R
import com.example.news.data.entity.Articles
import com.example.news.util.FetchTheLogo

@Composable
fun SearchItemDesign(article: Articles, onClick: () -> Unit){

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .padding(8.dp)
        .clickable{
            onClick()
        },
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically){

        AsyncImage(model = ImageRequest.Builder(LocalContext.current)
            .data(article.urlToImage)
            .error(R.drawable.outline_ar_stickers_24).build(),
            contentDescription = "",
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .fillMaxHeight()
            .width(84.dp),
            contentScale = ContentScale.Crop)

        Column(modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly){

            Text(text = article.title,
                maxLines = 2,
                lineHeight = 18.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 16.sp)

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {

                AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                    .data(FetchTheLogo(article))
                    .error(R.drawable.outline_ar_stickers_24).build(),
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape))

                Text(text = article.source.name,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 14.sp)
            }




        }

    }

}