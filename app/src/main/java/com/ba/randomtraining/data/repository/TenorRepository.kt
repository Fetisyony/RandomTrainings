package com.ba.randomtraining.data.repository

import com.ba.randomtraining.data.model.JasonSearchResultItem


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

interface TenorRepository {
    suspend fun getJasonsInitial(): TenorRequestResult

    suspend fun getJasonsNext(): TenorRequestResult
}