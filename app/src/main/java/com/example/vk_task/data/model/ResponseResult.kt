package com.example.vk_task.data.model

sealed class ResponseResult<out T> {
    data class Success<T>(val data: T) : ResponseResult<T>()
    data class Error(val e: Throwable) : ResponseResult<Nothing>()
}
