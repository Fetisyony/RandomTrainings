package com.ba.randomtraining.utils

import com.ba.randomtraining.data.api.ApiService
import com.ba.randomtraining.data.repository.ExerciseRepository
import com.ba.randomtraining.data.repository.ExerciseRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://exercisedb.p.rapidapi.com/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    val exerciseRepository: ExerciseRepository by lazy {
        ExerciseRepositoryImpl(apiService)
    }
}