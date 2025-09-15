package com.example.news.ui.carddesigns

import android.graphics.Paint
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.news.R

@Composable
fun CardDesignForCategories(drawable: Int, title: String, isSelected: Boolean = false, onClick: () -> Unit){
    Column(verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier
                .height(56.dp)
                .aspectRatio(1f / 1f)
                .clickable(indication = null,
                    interactionSource = remember
                    { MutableInteractionSource() }){
                    onClick()
                },
            elevation = CardDefaults.cardElevation(2.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {

            Image(
                painter = painterResource(drawable),
                contentDescription = "",
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
                ,
                colorFilter = ColorFilter.tint(if (isSelected)MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground),
                contentScale = ContentScale.Fit
            )

        }

        Text(
            text = title,
            fontSize = 16.sp,
            modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally)
        )

    }
}