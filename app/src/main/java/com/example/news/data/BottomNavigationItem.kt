package com.example.news.data

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
