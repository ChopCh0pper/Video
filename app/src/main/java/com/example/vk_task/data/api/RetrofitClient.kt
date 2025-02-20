package com.example.vk_task.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://pixabay.com/"

    private fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val videoApiService: VideoApiService = provideRetrofit().create(VideoApiService::class.java)

//    companion object {
//        private const val BASE_URL = "https://api.pexels.com/"
//        private const val API_KEY = "48938892-e56853779c76b397d48f48ba1"
//    }
}