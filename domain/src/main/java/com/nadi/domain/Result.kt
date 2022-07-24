package com.nadi.domain

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val results: T) : Result<T>()
    data class Failed(val exception: Exception? = null) : Result<Nothing>()
}