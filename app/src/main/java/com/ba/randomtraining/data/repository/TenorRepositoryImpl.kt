package com.ba.randomtraining.data.repository

import android.util.Log
import coil3.network.HttpException
import com.ba.randomtraining.data.api.ApiTenorService
import com.ba.randomtraining.data.model.JasonResponse
import java.io.IOException


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
                TenorRequestResult.Error(FetchError.NoDataLeftError)
            }
        } catch (e: IOException) {
            TenorRequestResult.Error(FetchError.NetworkError)
        } catch (e: HttpException) {
            TenorRequestResult.Error(FetchError.ServerError)
        } catch (e: Exception) {
            Log.d("TEST", "${e.stackTrace}")
            TenorRequestResult.Error(FetchError.UnexpectedError("Unexpected error occurred while loading"))
        }
    }
}
