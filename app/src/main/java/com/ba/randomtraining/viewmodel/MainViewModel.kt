package com.ba.randomtraining.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ba.randomtraining.data.model.JasonSearchResultItem
import com.ba.randomtraining.data.repository.TenorRepository
import com.ba.randomtraining.data.repository.TenorRequestResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModelFactory(private val tenorRepository: TenorRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(tenorRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

sealed class FetchError {
    data object Ok : FetchError() {
        override fun getErrorMessage(): String = "Ok"
    }
    data object NetworkError : FetchError() {
        override fun getErrorMessage(): String = "Error happened while loading.\nPlease check your internet connection."
    }
    // 5xx HTTP errors
    data object ServerError : FetchError() {
        override fun getErrorMessage(): String = "Error happened while loading.\nThe server is currently unavailable. Please"
    }
    // 4xx HTTP errors
    data object ClientError : FetchError() {
        override fun getErrorMessage(): String = "Error happened while loading.\nThere seems to be an issue with your request."
    }
    // Last page reached
    data object NoDataLeftError : FetchError() {
        override fun getErrorMessage(): String = "You have reached the end of the lane"
    }
    // Any other
    data class UnexpectedError(val message: String) : FetchError() {
        override fun getErrorMessage(): String = "Unexpected error while loading"

        fun getTechErrorMessage(): String = "Unexpected error: $message"
    }

    abstract fun getErrorMessage(): String
}

data class ErrorStatus(
    var updated: Boolean,
    var fetchError: FetchError
)

class MainViewModel(private val tenorRepository: TenorRepository) : ViewModel() {
    private val _jasonItems = MutableStateFlow<List<JasonSearchResultItem>>(emptyList())
    val jasonItems: StateFlow<List<JasonSearchResultItem>> = _jasonItems.asStateFlow() // Expose as StateFlow

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _errorStatus = MutableStateFlow(ErrorStatus(true, FetchError.Ok))
    val errorStatus: StateFlow<ErrorStatus> = _errorStatus.asStateFlow()

    init {
        fetchJason()
    }

    fun fetchJason(refresh: Boolean = false) {
        viewModelScope.launch {
            Log.d("TEST",  "cccccccccccccccccccccccccccccccccccc")
            if (isLoading.value || errorStatus.value.fetchError == FetchError.NoDataLeftError) return@launch

            _isLoading.value = true

            val newJasonItems: TenorRequestResult
            if (refresh) {
                newJasonItems = tenorRepository.getJasonsInitial()
                _isRefreshing.value = true
            } else
                newJasonItems = tenorRepository.getJasonsNext()

            when (newJasonItems) {
                is TenorRequestResult.Success -> {
                    if (refresh)
                        _jasonItems.value = newJasonItems.gifs
                    else
                        _jasonItems.value += newJasonItems.gifs
                    _errorStatus.value.fetchError = FetchError.Ok
                }
                is TenorRequestResult.Empty -> {
                    _errorStatus.value.fetchError = FetchError.NoDataLeftError
                }
                is TenorRequestResult.Error -> {
                    _errorStatus.value.fetchError = FetchError.NetworkError
                }
            }
            _isLoading.value = false
            _isRefreshing.value = false
            _errorStatus.value.updated = true
        }
    }
}
