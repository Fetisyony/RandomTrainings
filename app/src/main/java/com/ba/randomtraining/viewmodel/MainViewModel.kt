package com.ba.randomtraining.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ba.randomtraining.data.model.ExerciseResponse
import com.ba.randomtraining.data.repository.ExerciseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MainViewModel(private val repository: ExerciseRepository) : ViewModel() {

    private val _exercises = MutableStateFlow<List<ExerciseResponse>>(emptyList())
    val exercises: StateFlow<List<ExerciseResponse>> = _exercises.asStateFlow() // Expose as StateFlow

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private var currentPage = 1
    private var isLastPage = false

    init {
        fetchExercises()
    }

    fun fetchExercises(refresh: Boolean = false) {
        viewModelScope.launch {
            if (isLoading.value || isLastPage) return@launch

            try {
                val newExercises = repository.getExercises(page = currentPage)
                _isLoading.value = true
                if (refresh) {
                    _isRefreshing.value = true
                    currentPage = 1
                    isLastPage = false

                    _exercises.value = newExercises
                } else {
                    _exercises.value += newExercises
                }

                if (newExercises.isEmpty()) {
                    isLastPage = true
                } else {
                    currentPage++
                }
            } catch (e: Exception) {
                println("Error fetching exercises: ${e.message}")
            } finally {
                _isLoading.value = false
                _isRefreshing.value = false
            }
        }
    }
}
