package com.ba.randomtraining.data.repository

import coil3.network.HttpException
import com.ba.randomtraining.data.api.ApiTenorService
import com.ba.randomtraining.data.model.JasonResponse
import com.ba.randomtraining.data.model.JasonSearchResultItem
import java.io.IOException


sealed class FetchError {
    data object Ok : FetchError() {
        override fun getErrorMessage(): String = "Ok"
    }
    data object NetworkError : FetchError() {
        override fun getErrorMessage(): String = "An error occurred while loading.\nPlease check your internet connection."
    }
    // Last page reached
    data object NoDataLeftError : FetchError() {
        override fun getErrorMessage(): String = "You have reached the end of the lane"
    }
    // Any other
    data class UnexpectedError(val message: String) : FetchError() {
        override fun getErrorMessage(): String = "Unexpected error while loading"

        fun getTechErrorMessage(): String = "Unexpected error: $message"
    }

    abstract fun getErrorMessage(): String
}

sealed class TenorRequestResult {
    data class Success(val gifs: List<JasonSearchResultItem>) : TenorRequestResult()
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
                TenorRequestResult.Error(FetchError.NoDataLeftError)
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
