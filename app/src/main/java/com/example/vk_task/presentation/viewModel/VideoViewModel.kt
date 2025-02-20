package com.example.vk_task.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vk_task.data.api.RetrofitClient
import com.example.vk_task.data.repository.VideoRepository
import com.example.vk_task.presentation.state.VideoListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VideoViewModel: ViewModel() {

    private val _state = MutableStateFlow<VideoListState>(VideoListState.Loading)
    val state = _state.asStateFlow()

    private val videoRepository = VideoRepository(RetrofitClient.videoApiService)

    init {
        loadVideoList()
    }

    fun loadVideoList() {
        viewModelScope.launch {
            _state.value = VideoListState.Loading
            val currentList = videoRepository.getVideos()
            _state.value = VideoListState.Content(currentList)
        }
    }
}