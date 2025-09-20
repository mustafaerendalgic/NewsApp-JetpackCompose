package com.example.news.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.news.R

@Preview(showBackground = true)
@Composable
fun SignInPage(){

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.courier),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(0.6f).fillMaxHeight(0.1f),
            contentScale = ContentScale.Crop)

        Column(modifier = Modifier
            .fillMaxWidth(0.8f)
        ){

            OutlinedTextField(value = "",
                onValueChange = {},
                modifier = Modifier,
                label = {
                    Text(text = "E-mail")},
                leadingIcon = {
                    Icon(painter = painterResource(R.drawable.user), contentDescription = "")
                })

            OutlinedTextField(value = "",
                onValueChange = {},
                modifier = Modifier.padding(top = 8.dp),
                label = {
                    Text(text = "Password")},
                leadingIcon = {
                    Icon(Icons.Outlined.VpnKey, contentDescription = "")
                })

            Row {

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {},
                    modifier = Modifier.padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Sign In",
                        fontFamily = FontFamily(Font(R.font.gabarito))
                    )
                }


            }

        }
    }

}

