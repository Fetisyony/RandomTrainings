package com.ba.randomtraining.data.utils

import com.ba.randomtraining.data.api.ApiTenorService
import com.ba.randomtraining.data.repository.TenorRepository
import com.ba.randomtraining.data.repository.TenorRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitTenorInstance {
    private const val BASE_URL = "https://tenor.googleapis.com/v2/"

    private val apiTenorService: ApiTenorService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiTenorService::class.java)
    }

    val tenorRepository: TenorRepository by lazy {
        TenorRepositoryImpl(apiTenorService)
    }
}
