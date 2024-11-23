package com.ba.randomtraining.data.repository

import com.ba.randomtraining.data.model.JasonSearchResultItem


sealed class TenorRequestResult {
    data class Success(val gifs: List<JasonSearchResultItem>) : TenorRequestResult()
    data class Error(val exception: Exception) : TenorRequestResult()
    data object Empty : TenorRequestResult()
}

interface TenorRepository {
    suspend fun getJasonsInitial(): TenorRequestResult

    suspend fun getJasonsNext(): TenorRequestResult
}