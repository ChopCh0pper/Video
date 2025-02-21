package com.example.vk_task.di

import com.example.vk_task.data.api.RetrofitClient
import org.koin.dsl.module

val networkModule = module {

    single { RetrofitClient().videoApiService }
}