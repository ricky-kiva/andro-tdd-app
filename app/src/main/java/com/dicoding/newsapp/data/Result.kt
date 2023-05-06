package com.dicoding.newsapp.data

// <out R> means the R parameter is covariant. It makes Result<A> is also a subtype of Result<B>
sealed class Result<out R> private constructor() {
    // `: Result<T>()` means Success is a subtype of Result with generic parameter of T
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}