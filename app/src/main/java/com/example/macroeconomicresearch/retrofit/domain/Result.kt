package com.example.macroeconomicresearch.retrofit.domain


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Empty : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
            Empty -> "Empty"
            else ->""
        }
    }
}


inline fun <reified T> Call<T>.enqueue(crossinline result: (Result<Response<T>>) -> Unit) {
    enqueue(object: Callback<T> {
        override fun onFailure(call: Call<T>, error: Throwable) {
            result(Result.Error(Exception(error)))
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            result(Result.Success(response))
        }
    })
}



/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded
    get() = this is Result.Success && data != null

fun <T> Result<T>.successOr(fallback: T): T {
    return (this as? Result.Success<T>)?.data ?: fallback
}