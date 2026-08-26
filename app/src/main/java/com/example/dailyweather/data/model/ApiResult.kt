package com.example.dailyweather.data.model

interface ApiResult <out T> {

    data class Success<out T>(val data: T) : ApiResult<T>

    data class Failure(val errorMessage: String, val causeMessage: String) : ApiResult<Nothing>
}