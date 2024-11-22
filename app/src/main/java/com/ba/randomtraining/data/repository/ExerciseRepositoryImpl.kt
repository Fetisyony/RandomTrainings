package com.ba.randomtraining.data.repository

import com.ba.randomtraining.data.api.ApiService
import com.ba.randomtraining.data.model.ExerciseResponse

class ExerciseRepositoryImpl(
    private val apiService: ApiService
) : ExerciseRepository {
    override suspend fun getExercises(): List<ExerciseResponse> {
        return try {
            apiService.fetchExercises()
        } catch (e: Exception) {
            throw e
        }
    }

}