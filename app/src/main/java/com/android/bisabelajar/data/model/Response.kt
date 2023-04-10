package com.android.bisabelajar.data.model

sealed class Response<out T> {
    object Loading: Response<Nothing>()

    data class Success<out T>(
        val data: T
    ): Response<T>()

    data class Error(
        val e: Exception?
    ): Response<Nothing>()
}