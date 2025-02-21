package com.example.vk_task.data.api

import com.example.vk_task.data.model.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface VideoApiService {

    @GET("api/videos/")
    suspend fun getVideos(
        @Query("key") key: String = "48938892-e56853779c76b397d48f48ba1",
        @Query("per_page") perPage: Int = 40,
        @Query("category") category: String
    ): Response<VideoResponse>
}