package com.nadi.data

import com.google.gson.Gson
import com.nadi.domain.Result
import retrofit2.Response


abstract class CallbackHandler {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = call.invoke()
            val result: Result<T> = if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Failed(
                    RuntimeException(
                        Gson().fromJson(
                            response.errorBody()?.string(),
                            Result.Failed::class.java
                        ).exception
                    )
                )
            }
            result

        } catch (e: Exception) {
            Result.Failed(e)
        }
    }
}