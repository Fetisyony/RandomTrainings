package com.ba.randomtraining.data.api

import com.ba.randomtraining.data.model.ExercisesResponse
import retrofit2.http.GET
import retrofit2.http.Headers

const val API_KEY = "985ad85bc4mshe57e033c8b9ebddp1bc015jsn738f1c498f7c"

interface ApiService {
    @Headers("X-RapidAPI-Key: $API_KEY")
    @GET("exercises")
    suspend fun fetchExercises(): List<ExercisesResponse>
}
