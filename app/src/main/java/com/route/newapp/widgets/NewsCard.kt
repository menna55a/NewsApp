package com.route.newsappc41gsunwed.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.route.newapp.R
import com.route.newapp.api.model.ArticlesItem
import com.route.newapp.ui.theme.gray

@Composable
fun NewsCard(articleItem: ArticlesItem, modifier: Modifier = Modifier) {
    Card(
        modifier
            .fillMaxWidth()
            .border(1.dp, Color.White, RoundedCornerShape(10.dp))
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        ),

        ) {
        AsyncImage(
            model = articleItem.urlToImage,
            contentDescription = "Specific News Image ",
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = articleItem.title ?: "",
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.W700,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = stringResource(id = R.string.by) + "${articleItem.author}",
            color = gray,
            fontSize = 10.sp,
            fontWeight = FontWeight.W500
        )

    }
}