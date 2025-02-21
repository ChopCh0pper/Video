package com.example.vk_task.di

import com.example.vk_task.data.repository.VideoRepository
import org.koin.dsl.module

val dataModule = module {

    single {
        VideoRepository(get())
    }
}