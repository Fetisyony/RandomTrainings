package com.ba.randomtraining.data.repository

import coil3.network.HttpException
import com.ba.randomtraining.data.api.ApiTenorService
import com.ba.randomtraining.data.model.JasonResponse
import com.ba.randomtraining.data.model.JasonSearchResultItem
import java.io.IOException


sealed class FetchError {
    data object Ok : FetchError()
    data object NetworkError : FetchError()
    data object NoDataLeftError : FetchError() // Last page reached
    // Any other
    data class UnexpectedError(val message: String) : FetchError() {
        fun getTechErrorMessage(): String = "Unexpected error: $message"
    }
}

sealed class TenorRequestResult {
    data class Success(val gifs: List<JasonSearchResultItem>) : TenorRequestResult()
    data object Empty : TenorRequestResult()
    data class Error(val fetchError: FetchError) : TenorRequestResult()
}

class TenorRepository (
    private val apiTenorService: ApiTenorService
) {
    private var nextPosition: String? = ""

    suspend fun getJasonsInitial(): TenorRequestResult {
        nextPosition = ""
        return getJasonsNext()
    }

    suspend fun getJasonsNext(): TenorRequestResult {
        return try {
            if (nextPosition != null) {
                val response: JasonResponse = apiTenorService.fetchJason(pos = nextPosition!!)
                nextPosition = response.next
                TenorRequestResult.Success(response.results)
            } else {
                TenorRequestResult.Empty
            }
        } catch (e: Exception) {
            when (e) {
                is IOException, is HttpException -> {
                    TenorRequestResult.Error(FetchError.NetworkError)
                }
                else -> TenorRequestResult.Error(FetchError.UnexpectedError("Unexpected error occurred while loading"))
            }
        }
    }
}
