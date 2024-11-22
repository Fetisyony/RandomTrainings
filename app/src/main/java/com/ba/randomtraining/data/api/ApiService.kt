package com.ba.randomtraining.data.api

import com.ba.randomtraining.data.model.ExerciseResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val API_KEY = "0f68e6195cmshd6f975ded30d761p150c8bjsn400e212526cd"

interface ApiService {
    @Headers("X-RapidAPI-Key: $API_KEY")
    @GET("exercises")
    suspend fun fetchExercises(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 20
    ): List<ExerciseResponse>
}
