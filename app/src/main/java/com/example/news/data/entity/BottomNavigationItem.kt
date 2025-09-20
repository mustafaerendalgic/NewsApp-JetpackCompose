package com.example.news.data.entity

import android.graphics.drawable.Drawable
import android.media.Image
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val route: String,
    val selectedIcon: Painter
)
