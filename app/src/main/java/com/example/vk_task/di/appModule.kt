package com.example.vk_task.di

import com.example.vk_task.presentation.viewModel.VideoPlayerViewModel
import com.example.vk_task.presentation.viewModel.VideoViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        VideoViewModel(get())
    }

    viewModel {
        VideoPlayerViewModel(get())
    }
}