package com.example.news.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.news.R
import com.google.firebase.auth.FirebaseAuth


@Composable
fun SignUpPage(navController: NavController){

    SignUpPageUI(navController)

}

@Composable
fun SignUpPageUI(navController: NavController){

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Column(modifier = Modifier
            .fillMaxWidth(0.8f)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Image(painter = painterResource(R.drawable.courier),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(0.6f).fillMaxHeight(0.1f),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                contentScale = ContentScale.Crop)

            var eMail by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            OutlinedTextField(value = eMail,
                onValueChange = {eMail = it},
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "E-mail")},
                leadingIcon = {
                    Icon(painter = painterResource(R.drawable.user), contentDescription = "")
                })

            OutlinedTextField(value = password,
                onValueChange = {password = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                label = {
                    Text(text = "Password",
                        fontSize = 16.sp)},
                leadingIcon = {
                    Icon(Icons.Outlined.VpnKey, contentDescription = "")
                })

            val context = LocalContext.current

            Row(modifier = Modifier.padding(end = 8.dp)) {

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {

                        if(eMail.isNotBlank() && password.isNotBlank()){

                            FirebaseAuth.getInstance()
                                .createUserWithEmailAndPassword(eMail.trim(), password).addOnSuccessListener {

                                    Toast.makeText(context, "Başarılı", Toast.LENGTH_SHORT).show()

                                    navController.navigate("signin"){

                                        popUpTo("signup") {inclusive = true}

                                    }

                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, it.message ?: "Başarısız", Toast.LENGTH_SHORT).show()
                                }

                        }
                        else{

                            Toast.makeText(context, "Fields can not be empty", Toast.LENGTH_SHORT).show()

                        }

                    },
                    modifier = Modifier.padding(top = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Sign Up",
                        fontFamily = FontFamily(Font(R.font.gabarito)),
                        fontSize = 16.sp
                    )
                }


            }

            val annotatedString = buildAnnotatedString {

                append("Do you have an account? ")

                pushStringAnnotation(tag = "create_account", annotation = "create_account")

                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary))  {

                    append("Sign In")

                }

                pop()

            }

            ClickableText(text = annotatedString, modifier = Modifier
                .padding(top = 48.dp),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.gabarito)),
                    color = MaterialTheme.colorScheme.onBackground,
                ),
                onClick = { offset: Int ->

                    annotatedString.getStringAnnotations(tag = "create_account", start = offset, end = offset).firstOrNull()?.let { annotation ->
                        navController.navigate("signin"){
                            popUpTo(0){inclusive = true}
                        }
                    }

                }

            )

        }

    }

}

@Preview(showBackground = true)
@Composable
fun dummyPreva(){
    val navController = rememberNavController()
    SignUpPageUI(navController)
}

