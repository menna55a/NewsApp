package com.route.newapp.api

import com.route.newapp.api.model.NewsResponse
import com.route.newapp.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsServices {
    @GET("top-headlines/sources")
    fun getSources(
        @Query("category") categoryId: String,
        @Query("apiKey") apiKey: String = ApiManager.API_KEY
    ):Callback<SourcesResponse>

    @GET("everything")
    fun getNewsBySource(
        @Query("sources") source: String,
        @Query("apiKey") apiKey: String = ApiManager.API_KEY
    ): Call<NewsResponse>
}