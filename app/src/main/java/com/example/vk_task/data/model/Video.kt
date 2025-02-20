package com.example.vk_task.data.model


data class VideoResponse(
    val hits: List<VideoHit>
)

data class VideoHit(
    val id: Int,
    val duration: Int,
    val videos: VideoFormats
)

data class VideoFormats(
    val medium: Video?,
)

data class Video(
    val url: String,
    val thumbnail: String
)

