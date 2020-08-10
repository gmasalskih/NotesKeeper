package ru.gmasalskikh.noteskeeper.data.model

sealed class Result {
    class Success<out T>(val data: T? = null) : Result()
    class Error(val err: Throwable) : Result()
}