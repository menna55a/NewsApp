package com.route.newapp.news

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.route.newapp.R
import com.route.newapp.api.ApiManager
import com.route.newapp.api.model.ArticlesItem
import com.route.newapp.api.model.NewsResponse
import com.route.newapp.api.model.SourcesItem
import com.route.newapp.api.model.SourcesResponse
import com.route.newapp.ui.theme.gray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun NewsScreenContent(endpointId: String, modifier: Modifier = Modifier) {
    val selectedSourceId = remember {
        mutableStateOf("")
    }
    val newsListStates = remember {
        mutableStateListOf<ArticlesItem>()
    }
    val sourcesListStates = remember {
        mutableStateListOf<SourcesItem>()
    }
    LaunchedEffect(Unit) {
        getSources(endpointId = endpointId, onSuccess = {
            sourcesListStates.addAll(it)
        }, onFailure = {
            Log.e("TAG", "NewsScreenContent: $it")
        })
    }
    LaunchedEffect(selectedSourceId.value) {
        if (selectedSourceId.value.isNotEmpty())
            ApiManager.newsServices.getNewsBySource(selectedSourceId.value)
                .enqueue(object : Callback<NewsResponse> {
                    override fun onResponse(
                        p0: Call<NewsResponse>,
                        response: Response<NewsResponse>
                    ) {
                        val list = response.body()?.articles
                        if (list != null) {
                            newsListStates.clear()
                            newsListStates.addAll(list)
                        }
                    }

                    override fun onFailure(p0: Call<NewsResponse>, p1: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
    }
    Column(modifier) {
        if (sourcesListStates.isNotEmpty())
            SourcesTabRow(
                sourcesList = sourcesListStates,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                newsListStates.clear()
                selectedSourceId.value = it
            }
        NewsList(newsList = newsListStates)
    }
}


@Composable
fun NewsList(newsList: List<ArticlesItem>, modifier: Modifier = Modifier) {
    LazyColumn {
        items(newsList) {
            NewsCard(articleItem = it)
        }
    }
}

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

@Preview(showSystemUi = true)
@Composable
private fun NewsCardPreview() {
    NewsCard(
        articleItem = ArticlesItem(
            author = "Jon Haworth",
            title = "40-year-old man falls 200 feet to his death while canyoneering at national park",

            )
    )
}

@Composable
fun SourcesTabRow(
    sourcesList: List<SourcesItem>,
    modifier: Modifier = Modifier,
    onSourceSelected: (id: String) -> Unit
) {
    // Create State ->
    val selectedItemIndex = remember {
        mutableIntStateOf(0)
    }
    LaunchedEffect(Unit) {
        onSourceSelected(sourcesList.get(0).id ?: "")
    }
    val selectedModifier = Modifier.drawBehind {
        val strokeWidthPx = 2.dp.toPx()
        val verticalOffset = size.height - 2.sp.toPx()
        drawLine(
            color = Color.White,
            strokeWidth = strokeWidthPx,
            start = Offset(0f, verticalOffset),
            end = Offset(size.width, verticalOffset)
        )
    }
    LazyRow(modifier.background(Color.Black)) {
        itemsIndexed(sourcesList) { index, sourceItem ->
            Tab(
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White,
                selected = selectedItemIndex.intValue == index,
                onClick = {
                    Log.e("TAG", "SourcesTabRow:  $index")
                    onSourceSelected(sourceItem.id ?: "")
                    selectedItemIndex.intValue = index
                },
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = sourceItem.name ?: "",
                    color = Color.White,
                    modifier = if (selectedItemIndex.intValue == index) selectedModifier else Modifier
                )
//                    if (selectedItemIndex.intValue == index)
//                        HorizontalDivider(
//                            modifier = Modifier.height(2.dp),
//                            color = Color.White,
//                            thickness = 1.dp
//                        )

            }
        }
    }
}

fun getSources(
    endpointId: String,
    onSuccess: (sources: List<SourcesItem>) -> Unit,
    onFailure: (message: String) -> Unit
) {
    ApiManager.newsServices.getSources(categoryId = endpointId)
//                        .execute() // Run On Main Thread
        .enqueue(object : Callback<SourcesResponse> {
            override fun onFailure(
                p0: Call<SourcesResponse>,
                throwable: Throwable
            ) {
                Log.e("TAG", "onFailure: ${throwable.message}")

                onFailure(throwable.message ?: "Something Went Wrong")
            }

            override fun onResponse(
                call: Call<SourcesResponse>,
                response: Response<SourcesResponse>
            ) {
                Log.e("TAG", "onResponse: ${response}")
                Log.e("TAG", "onResponse: ${response.body()?.sources}")
                val list = response.body()?.sources
                if (list?.isNotEmpty() == true)
                    onSuccess(list)
            }
        }) // Run on Background Thread and Returns Result On Main Thread
}

@Preview(showSystemUi = true)
@Composable
private fun SourcesTabRowPreview() {
    SourcesTabRow(
        sourcesList = listOf(
            SourcesItem(name = "ABC News"),
            SourcesItem(name = "Al-Jazeera"),
            SourcesItem(name = "BBC News")
        )
    ) {

    }
}
