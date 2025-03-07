package com.example.vk_task.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vk_task.data.model.ResponseResult
import com.example.vk_task.data.repository.VideoRepository
import com.example.vk_task.presentation.state.VideoListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VideoViewModel(
    private val videoRepository: VideoRepository
): ViewModel() {

    private val _state = MutableStateFlow<VideoListState>(VideoListState.Loading)
    val state = _state.asStateFlow()

    init {
        loadVideoList()
    }

    fun loadVideoList() {
        viewModelScope.launch {
            _state.value = VideoListState.Loading
            when(val responseResult = videoRepository.getVideos()) {

                is ResponseResult.Success -> {
                    _state.value = VideoListState.Content(responseResult.data)
                }

                is ResponseResult.Error -> {
                    _state.value = VideoListState.Error(responseResult.e)
                }
            }
        }
    }
}