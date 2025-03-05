package com.route.newsappc41gsunwed.widgets

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.route.newapp.R
import com.route.newapp.api.model.ArticlesItem
import com.route.newapp.news.NewsViewModel
import com.route.newapp.ui.theme.gray


@Composable
fun NewsList(viewModel: NewsViewModel) {

    LazyColumn {
        items(viewModel.newsListStates) {
            NewsCard(articleItem = it, modifier = Modifier.clickable {
                viewModel.selectedArticle.value = it
                viewModel.openBottomSheet.value = true
            })
        }
    }

    if (viewModel.openBottomSheet.value) {
        ArticleDetailsBottomSheet(viewModel.selectedArticle.value) {
            viewModel.openBottomSheet.value = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailsBottomSheet(
    articleItem: ArticlesItem?,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {

    val sheetState = rememberModalBottomSheetState()
    val context = LocalContext.current
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismiss() },
        dragHandle = {},
        containerColor = Color.White
    ) {
        Card(
            modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.White
            ),

            ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {


                AsyncImage(
                    model = articleItem?.urlToImage,
                    contentDescription = "Specific News Image ",
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = articleItem?.title ?: "",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.W700,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = articleItem?.content ?: "",
                    color = gray,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.W500
                )

                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(articleItem?.url))
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier
                        .background(
                            Color.Black,
                            RoundedCornerShape(8.dp)
                        )
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.view_full_article),
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ArticleDetailsBottomSheetPreview() {
    ArticleDetailsBottomSheet(articleItem = ArticlesItem()) {}
}