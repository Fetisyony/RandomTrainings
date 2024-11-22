package com.ba.randomtraining.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ba.randomtraining.data.model.ExerciseResponse
import com.ba.randomtraining.data.repository.ExerciseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: ExerciseRepository
) : ViewModel() {
    private val _exercises = MutableStateFlow<List<ExerciseResponse>>(emptyList())
    val exercises: StateFlow<List<ExerciseResponse>> get() = _exercises

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        fetchExercises()
    }

    private fun fetchExercises() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _exercises.value = repository.getExercises()
            } catch (e: Exception) {
                _exercises.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
