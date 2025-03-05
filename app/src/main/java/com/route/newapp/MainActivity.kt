package com.route.newapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.route.newapp.categories.CategoriesScreen
import com.route.newapp.ui.theme.NewAppTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewAppTheme  {

                Scaffold(
                    topBar = {
                        NewsToolbar(title = "General")
                    },
                    containerColor = Color.Black
                ) { paddingValues ->
                    paddingValues
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = CategoriesScreen,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        // Login Screen - Register Screen- Forgot Password
                        composable<CategoriesScreen> {
                            CategoriesScreen(navController)
                        }
                        composable<NewsScreen> {
                            val endpointId = it.toRoute<NewsScreen>().endpointId
                            NewsScreenContent(endpointId)
                        }
                    }

                }
            }
        }


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsToolbar(
    title: String,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.width(1.dp))
                Text(text = title)
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(
                        R.string.news_search
                    )
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Black,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        ),
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = stringResource(R.string.navigation_menu_icon)
            )
        },
    )
}

@Preview
@Composable
private fun NewsToolbarPreview() {
    NewsToolbar(title = "General")
}


