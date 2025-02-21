package com.example.vk_task.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val videoApiService: VideoApiService = provideRetrofit().create(VideoApiService::class.java)

    companion object {
        private const val BASE_URL = "https://pixabay.com/"
    }
}