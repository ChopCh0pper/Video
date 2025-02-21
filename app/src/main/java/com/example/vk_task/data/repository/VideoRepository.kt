package com.example.vk_task.data.repository

import com.example.vk_task.data.api.VideoApiService
import com.example.vk_task.data.model.ResponseResult
import com.example.vk_task.data.model.VideoHit
import retrofit2.HttpException
import java.io.IOException

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

    suspend fun getVideos(): ResponseResult<List<VideoHit>> {
        return try {
            val category = generateCategory()
            val response = videoApiService.getVideos(category = category)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    ResponseResult.Success(body.hits)
                } else {
                    ResponseResult.Error(NullPointerException())
                }
            } else {
                ResponseResult.Error(HttpException(response))
            }
        } catch (e: Exception) {
            ResponseResult.Error(e)
        }
    }

    // Этот метод сделан для того, чтобы
    // при обновлении списка видео в приложении были разные результаты (в выбранной мной api
    // видео почти не добавляются в одних и тех же запросах)
    private fun generateCategory(): String {
        return categories.random()
    }
}