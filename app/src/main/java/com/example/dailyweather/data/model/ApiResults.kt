package com.example.dailyweather.data.model

interface ApiResults <out T> {

    data class Success<out T>(val data: T) : ApiResults<T>

    data class Failure(val errorMessage: String, val causeMessage: String) : ApiResults<Nothing>
}