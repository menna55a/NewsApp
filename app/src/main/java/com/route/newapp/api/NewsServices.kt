package com.route.newapp.api

import com.route.newapp.api.model.NewsResponse
import com.route.newapp.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsServices {
  @GET("top-headlines/sources")
  fun getSources(
    @Query("category") categoryId: String,
    @Query("apiKey") apiKey: String = ApiManager.API_KEY
  ): Call<SourcesResponse>

  @GET("everything")
  fun getNewsBySource(
    @Query("sources") source: String,
    @Query("apiKey") apiKey: String = ApiManager.API_KEY
  ): Call<NewsResponse>

  @GET("everything")
  fun searchNews(
    @Query("q") query: String,
    @Query("apiKey") apiKey: String = ApiManager.API_KEY
  ): Call<NewsResponse>

  /*@GET("top-headlines/sources")
  fun getSources(@Query("apiKey") apiKey: String = "900e01a88280474ca3bf161e289c3fe0"): Call<SourcesResponse>

  @GET("everything")
  fun getNewsBySource(
    @Query("sources") sources: String,
    @Query("apiKey") apiKey: String = "900e01a88280474ca3bf161e289c3fe0"
  ): Call<NewsResponse>*/

}