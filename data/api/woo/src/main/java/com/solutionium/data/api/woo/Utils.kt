package com.solutionium.data.api.woo

import android.util.Log
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result
import com.solutionium.data.network.adapter.NetworkResponse
import com.solutionium.data.network.response.WooErrorResponse

val TAG: String = "NetworkResponseHandler"

suspend fun <T : Any, R> handleNetworkResponse(
    networkCall: suspend () -> NetworkResponse<T, WooErrorResponse>,
    mapper: (T) -> R,
): Result<R, GeneralError> {

    return when (val result = networkCall()) {
        is NetworkResponse.Success -> {
            Log.d("TAG", "handleNetworkResponse: success")
            val response = result.body
            if (response != null) {
                Result.Success(mapper(response))
            } else {
                Result.Failure(GeneralError.UnknownError(Throwable("Response body is null")))
            }
        }
        is NetworkResponse.ApiError -> {
            val errorResponse = result.body
            Log.d("TAG", "${errorResponse.toString()} handleNetworkResponse: api error")
            Result.Failure(GeneralError.ApiError(errorResponse.message, errorResponse.code, errorResponse.data?.status))
        }
        is NetworkResponse.NetworkError -> {
            Log.d(TAG, "handleNetworkResponse: NetworkError")
            Result.Failure(GeneralError.NetworkError)
        }
        is NetworkResponse.UnknownError -> {
            Log.d(TAG, "handleNetworkResponse: UnknownError", result.error)
            Result.Failure(GeneralError.UnknownError(result.error))

        }
    }
}