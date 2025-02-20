package com.example.vk_task.data.repository

import com.example.vk_task.data.api.VideoApiService
import com.example.vk_task.data.model.Video
import com.example.vk_task.data.model.VideoHit
import com.google.android.exoplayer2.util.Log

class VideoRepository(
    private val videoApiService: VideoApiService
) {

    // Список возможных категорий
    private val categories = listOf(
        "backgrounds", "fashion", "nature", "science", "education", "feelings",
        "health", "people", "religion", "places", "animals", "industry",
        "computer", "food", "sports", "transportation", "travel", "buildings",
        "business", "music"
    )

    suspend fun getVideos(): List<VideoHit> {
        val category = generateCategory()
        return videoApiService.getVideos(category = category).hits
    }

    // Этот метод сделан для того, чтобы
    // при обновлении списка видео в приложении были разные результаты (в выбранной мной api
    // видео почти не добавляются в одних и тех же запросах)
    private fun generateCategory(): String {
        return categories.random()
    }
}