package com.example.vk_task.presentation.state

import com.example.vk_task.data.model.VideoHit

sealed class VideoListState {
    data object Loading : VideoListState()
    data class Content(val currentList: List<VideoHit>) : VideoListState()
    data class Error(val e: Throwable) : VideoListState()
}