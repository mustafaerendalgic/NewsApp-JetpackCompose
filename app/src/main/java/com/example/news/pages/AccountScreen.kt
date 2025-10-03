package com.example.news.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AccountScreen(paddingValues: PaddingValues, navController: NavController){
    Column(modifier = Modifier.fillMaxSize()
        .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign Out", color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable{
                FirebaseAuth.getInstance()
                    .signOut()
                navController.navigate("signin"){
                    popUpTo(0)
                }
            })

    }
}