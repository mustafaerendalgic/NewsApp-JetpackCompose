package com.example.news


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.news.data.entity.BottomNavigationItem
import com.example.news.pages.AccountScreen
import com.example.news.pages.DetailScreen
import com.example.news.pages.MainPage
import com.example.news.pages.MarkedScreen
import com.example.news.pages.SearchScreen
import com.example.news.pages.SignInPage
import com.example.news.pages.SignUpPage
import com.example.news.ui.theme.NewsTheme
import com.example.news.ui.viewmodels.MainPageViewModel
import com.example.news.ui.viewmodels.MarkedViewModel
import com.example.news.ui.viewmodels.SearchPageViewModel
import com.google.firebase.auth.FirebaseAuth
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

    Log.d("API_KEY_CHECK", "API key: ${BuildConfig.API_KEY}")

    val items: List<BottomNavigationItem> = listOf(

        BottomNavigationItem(route = "home", selectedIcon = painterResource(R.drawable.home)),

        BottomNavigationItem(route = "search", selectedIcon = painterResource(R.drawable.magnifyingglass)),

        BottomNavigationItem(route = "marks", selectedIcon = painterResource(R.drawable.bookmark)),

        BottomNavigationItem(route = "account", selectedIcon = painterResource(R.drawable.user))
    )

    val navigationCont = rememberNavController()
    val backStackEntry by navigationCont.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val notBarsList = listOf("signin", "signup")
    val showBars = !notBarsList.contains(currentRoute)

    Scaffold(
        topBar = {
            if(showBars) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .background(MaterialTheme.colorScheme.background)
                ) {

                    Row(
                        modifier = Modifier
                            .height(64.dp)
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {

                        Text(
                            text = "courier",
                            fontSize = 28.sp,
                            fontFamily = FontFamily(Font(R.font.gabarito)),
                            modifier = Modifier
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Icon(
                            painterResource(R.drawable.magnifyingglass),
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )

                        Icon(
                            painterResource(R.drawable.notification),
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )

                    }

                }
            }
        },
        bottomBar = {
            if(showBars) {
                NavigationBar(containerColor = MaterialTheme.colorScheme.background) {

                    items.forEach {

                        val selected = it.route == currentRoute

                        NavigationBarItem(
                            selected = selected,

                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent,
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onBackground
                            ),

                            onClick = {
                                navigationCont.navigate(it.route) {
                                    popUpTo(navigationCont.graph.startDestinationId) {
                                        saveState = true
                                    }
                                }
                            },
                            icon = {
                                if (!selected) Image(
                                    it.selectedIcon,
                                    modifier = Modifier.size(26.dp),
                                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                                    contentDescription = ""
                                ) else Image(
                                    it.selectedIcon,
                                    modifier = Modifier.size(26.dp),
                                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                                    contentDescription = ""
                                )
                            }

                        )

                    }

                }
            }
        }
    ){innerPadding ->

        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        val viewModel: MainPageViewModel = hiltViewModel()
        val markViewModel: MarkedViewModel = hiltViewModel()
        val searchViewModel: SearchPageViewModel = hiltViewModel()

        NavHost(
            navController = navigationCont,
            startDestination = if(currentUser != null) "home" else "signin",
            modifier = Modifier
                .fillMaxSize()
        ) {
            composable("signin") { SignInPage(navigationCont) }
            composable("signup") { SignUpPage(navigationCont) }
            composable("home") { MainPage(navigationCont, viewModel, innerPadding, markViewModel) }
            composable("search") { SearchScreen(paddingValues = innerPadding, searchViewModel = searchViewModel, navController = navigationCont, mainPageViewModel = viewModel) }
            composable("marks") { MarkedScreen(paddingValues = innerPadding, markViewModel) }
            composable("account") { AccountScreen(paddingValues = innerPadding, navigationCont) }
            composable("details") { DetailScreen(viewModel, innerPadding, navigationCont, markViewModel) }}

    }


}



