package com.route.newapp.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.route.newapp.NewsScreen
import com.route.newapp.R
import com.route.newapp.api.model.Category
import com.route.newapp.ui.theme.blackWith50Opacity


@Composable
fun CategoriesScreen(navHostController: NavHostController, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = stringResource(R.string.good_morning) +
                        stringResource(R.string.here_is_some_news_for_you),
                fontWeight = FontWeight.W500,
                color = Color.White,

                fontSize = 22.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), textAlign = TextAlign.Start
            )
        }
        val categoriesList = Category.getCategoriesList()
        items(categoriesList.size) { position ->
            CategoryCard(categoriesList.get(position), isRight = position % 2 == 0) { endpointId ->
                navHostController.navigate(NewsScreen(endpointId))
            }
        }


    }
}

@Composable
fun CategoryCard(
    category: Category,
    isRight: Boolean,
    modifier: Modifier = Modifier,
    onCategoryClick: (endpointId: String) -> Unit
) {
    Card(
        onClick = {
            onCategoryClick(category.endpointId ?: "")
        },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black),
        modifier = modifier
            .padding(vertical = 2.dp, horizontal = 4.dp)
            .fillMaxWidth(0.9F)
            .height(200.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isRight) {
                Image(
                    painter = painterResource(
                        id = category.drawableResId ?: R.drawable.news_image_logo
                    ), contentDescription = stringResource(R.string.category_image),
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.5F),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.weight(1F))
                Column(
                    modifier = Modifier.fillMaxHeight(1F),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = stringResource(id = category.titleResId ?: R.string.app_name),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    ViewAllRightArrowButton()
                }

            } else {
                Column(
                    modifier = Modifier.fillMaxHeight(1F),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = stringResource(id = category.titleResId ?: R.string.app_name),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    ViewAllLeftArrowButton()
                }
                Spacer(modifier = Modifier.weight(1F))
                Image(
                    painter = painterResource(
                        id = category.drawableResId ?: R.drawable.news_image_logo
                    ), contentDescription = stringResource(R.string.category_image),
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth(0.5F)
                        .scale(2F)
                )
            }
        }
    }
}

@Composable
fun ViewAllLeftArrowButton(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .background(blackWith50Opacity, shape = CircleShape),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.view_all_left_arrow),
            contentDescription = stringResource(
                R.string.category_view_all_content
            ),
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = stringResource(R.string.view_all),
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

    }
}

@Composable
fun ViewAllRightArrowButton(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .background(blackWith50Opacity, shape = CircleShape),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.view_all),
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.view_all_right_arrow),
            contentDescription = stringResource(
                R.string.category_view_all_content
            ),
            modifier = Modifier.size(30.dp)
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun CategoryCardRight() {
    CategoryCard(category = Category.getCategoriesList()[0], isRight = true) {

    }
}

@Preview
@Composable
private fun CategoryCardLeft() {
    CategoryCard(category = Category.getCategoriesList()[1], isRight = false) {

    }
}

@Preview
@Composable
private fun CategoriesScreenPreview() {
    CategoriesScreen(rememberNavController())
}