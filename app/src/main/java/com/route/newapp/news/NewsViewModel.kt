package com.route.newapp.news

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.route.newapp.api.ApiManager
import com.route.newapp.api.model.ArticlesItem
import com.route.newapp.api.model.NewsResponse
import com.route.newapp.api.model.SourcesItem
import com.route.newapp.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    val selectedSourceId = mutableStateOf("") // Observer Pattern
    val isLoading = mutableStateOf(false)
    val sourcesListStates = mutableStateListOf<SourcesItem>()
    val newsListStates = mutableStateListOf<ArticlesItem>()
    val errorState = mutableStateOf("")
    var openBottomSheet = mutableStateOf(false)
    var selectedArticle = mutableStateOf<ArticlesItem?>(null)
    val searchQuery = mutableStateOf("")
    val isFocused = mutableStateOf(true)

    fun getSources(endpointId: String) {
        isLoading.value = true
        ApiManager.newsServices.getSources(categoryId = endpointId)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onFailure(
                    p0: Call<SourcesResponse>,
                    throwable: Throwable
                ) {
                    isLoading.value = false
                    Log.e("TAG", "onFailure: ${throwable.message}")
                    errorState.value = "${throwable.message}"
                }

                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    isLoading.value = false
                    if (response.isSuccessful) {
                        Log.e("TAG", "onResponse: ${response}")
                        Log.e("TAG", "onResponse: ${response.body()?.sources}")
                        val list = response.body()?.sources
                        if (list?.isNotEmpty() == true)
                            sourcesListStates.addAll(list)
                    } else {
                        val json = response.errorBody()?.string()
                        val gson = Gson()
                        val sourcesResponse = gson.fromJson(json, SourcesResponse::class.java)
                        errorState.value = "${sourcesResponse.message}"
                    }
                }
            }) // Run on Background Thread and Returns Result On Main Thread
    }

    fun getNewsBySource() {

        if (selectedSourceId.value.isNotEmpty()) {
            isLoading.value = true
            ApiManager.newsServices.getNewsBySource(selectedSourceId.value)
                .enqueue(object : Callback<NewsResponse> {
                    override fun onResponse(
                        p0: Call<NewsResponse>,
                        response: Response<NewsResponse>
                    ) {
                        isLoading.value = false
                        if (response.isSuccessful) {
                            val list = response.body()?.articles
                            if (list != null) {
                                newsListStates.clear()
                                newsListStates.addAll(list)
                            }
                        } else {
                            val json = response.errorBody()?.string()
                            val gson = Gson()
                            val newsResponse = gson.fromJson(json, NewsResponse::class.java)
                            errorState.value = "${newsResponse.message}"
                        }
                    }

                    override fun onFailure(p0: Call<NewsResponse>, throwable: Throwable) {
                        isLoading.value = false
                        errorState.value = "${throwable.message}"
                    }

                })
        }
    }

    fun getNews() {
        isLoading.value = true
        ApiManager.newsServices.searchNews(searchQuery.value)
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    p0: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    isLoading.value = false
                    if (response.isSuccessful) {
                        val list = response.body()?.articles
                        if (list != null) {
                            newsListStates.clear()
                            newsListStates.addAll(list)
                        }
                    } else {
                        val json = response.errorBody()?.string()
                        val gson = Gson()
                        val newsResponse = gson.fromJson(json, NewsResponse::class.java)
                        errorState.value = "${newsResponse.message}"
                    }
                }

                override fun onFailure(p0: Call<NewsResponse>, throwable: Throwable) {
                    isLoading.value = false
                    errorState.value = "${throwable.message}"
                }

            })
    }
}
