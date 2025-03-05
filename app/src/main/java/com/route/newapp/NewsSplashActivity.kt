package com.route.newapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.route.newapp.ui.theme.NewAppTheme
import com.route.newapp.ui.theme.black

class NewsSplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewAppTheme {
                LaunchedEffect(Unit) {
                    Handler(Looper.getMainLooper()).postDelayed(
                        {
                            val intent = Intent(this@NewsSplashActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }, 2_500
                    )
                }
                SplashScreenContent()
            }
        }
    }
}

@Composable
fun SplashScreenContent(modifier: Modifier = Modifier) {
    Column (
        modifier
            .fillMaxSize()
            .background(black)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Spacer(modifier = Modifier.weight(1F))
        Image(
            painter = painterResource(id = R.drawable.news_app_logo),
            contentDescription = stringResource(R.string.news_app_logo),
            modifier = Modifier
                .fillMaxHeight(0.25F),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.weight(1F))
        Image(
            painter = painterResource(id = R.drawable.news_signature),
            contentDescription = stringResource(R.string.news_app_development_signature),
            modifier = Modifier
                .fillMaxWidth(0.38F),

            contentScale = ContentScale.Crop
        )

    }
}

@Preview(showSystemUi = true)
@Composable
private fun SplashScreenPreview(){
    SplashScreenContent()
}