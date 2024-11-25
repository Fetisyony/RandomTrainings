package com.ba.randomtraining.data.api

import com.ba.randomtraining.BuildConfig
import com.ba.randomtraining.data.model.JasonResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = BuildConfig.API_KEY
private const val LIMIT = 5
private const val REQUEST_STRING = "Jason Statham"
private const val CLIENT_KEY = "AndroidApp"

interface ApiTenorService {
    @GET("search")
    suspend fun fetchJason(
        @Query("q") request: String = REQUEST_STRING,
        @Query("pos") pos: String = "",
        @Query("limit") limit: Int = LIMIT,
        @Query("client_key") clientKey: String = CLIENT_KEY,
        @Query("key") key: String = API_KEY
    ): JasonResponse
}
