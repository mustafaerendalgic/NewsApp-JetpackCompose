package com.example.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.news.data.entity.BottomNavigationItem
import com.example.news.pages.AccountScreen
import com.example.news.pages.MainPage
import com.example.news.pages.MarkedScreen
import com.example.news.pages.SearchScreen

import com.example.news.ui.theme.NewsTheme
import dagger.hilt.EntryPoint

@EntryPoint
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

@Composable
fun Greeting(){

    var number by remember { mutableStateOf(0) }

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize()) {
            Text(text = number.toString())
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically) {
                OutlinedButton(onClick = {
                    number--
                },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(10.dp)) {

                    Text(text = "Çıkar")
                }
                Button(onClick = {number++},
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)) {
                    Text(text = "Ekle")
                }
            }
    }
}

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
            TopAppBar(title = { Text(text = "News") })
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
    ){padding ->
        NavHost(navController = navigationCont, startDestination = "home", modifier = Modifier.padding(padding)){
            composable("home") { MainPage(paddingValues = padding) }
            composable("search") { SearchScreen(paddingValues = padding) }
            composable("marks") { MarkedScreen(paddingValues = padding) }
            composable("account") { AccountScreen(paddingValues = padding) }
        }
    }


}



