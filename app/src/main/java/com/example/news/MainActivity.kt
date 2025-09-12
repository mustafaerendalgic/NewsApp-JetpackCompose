package com.example.news


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CircleNotifications
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.news.data.entity.BottomNavigationItem
import com.example.news.pages.AccountScreen
import com.example.news.pages.MainPage
import com.example.news.pages.MarkedScreen
import com.example.news.pages.SearchScreen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.news.pages.DetailScreen

import com.example.news.ui.theme.NewsTheme
import com.example.news.ui.viewmodels.MainPageViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BottomNav()
                }
            }
        }
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNav(){

    val items: List<BottomNavigationItem> = listOf(
        BottomNavigationItem(route = "home", selectedIcon = Icons.Filled.Newspaper, unselectedIcon = Icons.Outlined.Newspaper),
        BottomNavigationItem(route = "search", selectedIcon = Icons.Filled.Search, unselectedIcon = Icons.Outlined.Search),
        BottomNavigationItem(route = "marks", selectedIcon = Icons.Filled.Bookmark, unselectedIcon = Icons.Outlined.BookmarkBorder),
        BottomNavigationItem(route = "account", selectedIcon = Icons.Filled.Person, unselectedIcon = Icons.Outlined.Person)
    )

    var selectedIndex by remember {
        mutableStateOf(0)
    }

    val navigationCont = rememberNavController()
    val backStackEntry by navigationCont.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 12.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Column {
                    TopAppBar(
                        title = {
                            Text(
                                text = "news",
                                fontSize = 24.sp,
                                fontFamily = FontFamily(Font(R.font.playfair))
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        modifier = Modifier
                            .shadow(22.dp),
                    )

                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color.LightGray
                    )
                }
            }
        },
        bottomBar = {
            NavigationBar {
                items.forEach {
                    val selected = it.route == currentRoute
                    NavigationBarItem(selected = selected,
                        onClick = {
                            navigationCont.navigate(it.route){
                                popUpTo(navigationCont.graph.startDestinationId){
                                    saveState = true
                                }
                            }
                        },
                        icon = {
                            if (selected) Icon(
                                it.selectedIcon,
                                contentDescription = ""
                            ) else Icon(it.unselectedIcon, contentDescription = "")
                        }
                    )
                }
            }
        }
    ){innerPadding ->

        val viewModel: MainPageViewModel = hiltViewModel()

        NavHost(
            navController = navigationCont,
            startDestination = "home",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable("home") { MainPage(navigationCont, viewModel) }
            composable("search") { SearchScreen(paddingValues = innerPadding) }
            composable("marks") { MarkedScreen(paddingValues = innerPadding) }
            composable("account") { AccountScreen(paddingValues = innerPadding) }
            composable("details") { DetailScreen(viewModel) }}

    }


}



