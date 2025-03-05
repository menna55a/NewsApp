package com.route.newapp.news

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.route.newapp.api.model.ArticlesItem
import com.route.newapp.api.model.SourcesItem
import androidx.lifecycle.viewmodel.compose.viewModel
import com.route.newapp.widgets.NewsCard
import com.route.newapp.widgets.NewsList
import com.route.newapp.NewsToolbar
import com.route.newapp.widgets.ErrorDialog


@Composable
fun NewsScreen(
    endpointId: String,
    viewModel: NewsViewModel = viewModel(),
    modifier: Modifier = Modifier,
    onSearchClick:()->Unit
) {
    val sourcesList = viewModel.sourcesListStates
    val newsList = viewModel.newsListStates
    LaunchedEffect(Unit) {
        viewModel.getSources(endpointId)
    }
    LaunchedEffect(viewModel.selectedSourceId.value) {
        viewModel.getNewsBySource()

    }
    Scaffold(
        topBar = {
            NewsToolbar(title = "General"){
                onSearchClick()
            }
        },
        containerColor = Color.Black
    ) { paddingValues ->

        Column(modifier.padding(paddingValues)) {
            if (sourcesList.isNotEmpty())
                SourcesTabRow(
                    sourcesList = sourcesList,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    newsList.clear()
                    viewModel.selectedSourceId.value = it
                }
            NewsList(viewModel)
        }
    }

    if (viewModel.isLoading.value)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = Color.White)
        }

    if (viewModel.errorState.value.isNotEmpty())
        ErrorDialog(viewModel.errorState.value){
            viewModel.errorState.value=""
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


            }
        }
    }
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
