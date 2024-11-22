package com.ba.randomtraining.data.repository

import com.ba.randomtraining.data.model.ExerciseResponse

interface ExerciseRepository {
    suspend fun getExercises(page: Int): List<ExerciseResponse>
}