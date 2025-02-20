package com.example.vk_task.presentation.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class VideoPlayerViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        private const val KEY_POSITION = "playback_position"
        private const val KEY_PLAY_WHEN_READY = "play_when_ready"
    }

    var playbackPosition: Long
        get() = savedStateHandle[KEY_POSITION] ?: 0L
        set(value) {
            savedStateHandle[KEY_POSITION] = value
        }

    var playWhenReady: Boolean
        get() = savedStateHandle[KEY_PLAY_WHEN_READY] ?: true
        set(value) {
            savedStateHandle[KEY_PLAY_WHEN_READY] = value
        }
}
