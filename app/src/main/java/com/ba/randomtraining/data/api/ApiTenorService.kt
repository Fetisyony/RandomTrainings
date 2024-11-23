package com.ba.randomtraining.data.api

import com.ba.randomtraining.data.model.JasonResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "AIzaSyAFHIaxInINVAW8HKhV4_44qHsgYBLditI"
private const val LIMIT = 5

interface ApiTenorService {
    @GET("search")
    suspend fun fetchJason(
        @Query("q") request: String = "Jason Statham",
        @Query("pos") pos: String = "",
        @Query("limit") limit: Int = LIMIT,
        @Query("client_key") clientKey: String = "AndroidApp",
        @Query("key") key: String = API_KEY
    ): JasonResponse
}
