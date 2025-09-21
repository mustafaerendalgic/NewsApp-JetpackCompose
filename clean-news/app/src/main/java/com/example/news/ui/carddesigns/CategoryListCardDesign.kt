package com.example.news.ui.carddesigns

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Timelapse
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.news.R
import com.example.news.data.entity.Articles
import com.example.news.data.entity.Source
import com.example.news.util.FetchTheLogo
import com.example.news.util.ParseFunction

@Composable
fun CategoryListCardDesign(article: Articles, onClick: () -> Unit){
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp)
        .aspectRatio(16f/9f)
        .clickable{
            onClick()
        },
        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
        ){
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)){

            Row(modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(32.dp)) {

                AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                    .data(article.urlToImage)
                    .error(R.drawable.outline_ar_stickers_24)
                    .fallback(R.drawable.outline_ar_stickers_24)
                    .placeholder(R.drawable.outline_ar_stickers_24).build(),
                    contentDescription = "",

                    contentScale = ContentScale.Crop,

                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(3f/4f)
                        .clip(shape = RoundedCornerShape(16.dp)),

                )

                Column(modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly) {

                    Row(modifier = Modifier,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically) {


                        Text(text = if(article.author != null) article.author else article.source.name ,
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                            modifier = Modifier.weight(1f))

                        Icon(Icons.Rounded.Timelapse, tint = colorResource(R.color.mavi), contentDescription = "",
                            modifier = Modifier.size(20.dp))

                        Text(text = ParseFunction(article.publishedAt),
                            modifier = Modifier.width(64.dp),
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.gabarito)),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onBackground

                        )
                    }

                    Text(text = article.title.substringBeforeLast("-"),
                        fontSize = 16.sp,
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onBackground)

                    Row(modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                        AsyncImage(model = FetchTheLogo(article),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                                .clip(shape = CircleShape))

                        Text(text = article.source.name,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.gabarito)),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            color = MaterialTheme.colorScheme.onBackground

                        )
                    }

                }

            }

        }
    }
}
