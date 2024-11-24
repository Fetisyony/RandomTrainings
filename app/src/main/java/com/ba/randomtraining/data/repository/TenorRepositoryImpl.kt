package com.ba.randomtraining.data.repository

import coil3.network.HttpException
import com.ba.randomtraining.data.api.ApiTenorService
import com.ba.randomtraining.data.model.JasonResponse


class TenorRepositoryImpl(
    private val apiTenorService: ApiTenorService
) : TenorRepository {
    private var nextPosition: String? = ""

    override suspend fun getJasonsInitial(): TenorRequestResult {
        nextPosition = ""
        return getJasonsNext()
    }

    override suspend fun getJasonsNext(): TenorRequestResult {
        return try {
            if (nextPosition != null) {
                val response: JasonResponse = apiTenorService.fetchJason(pos = nextPosition!!)
                nextPosition = response.next
                TenorRequestResult.Success(response.results)
            } else {
                TenorRequestResult.Empty
            }
        } catch (e: Exception) {
            TenorRequestResult.Error(e)
        }
    }
}
