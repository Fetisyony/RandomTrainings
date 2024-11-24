package com.ba.randomtraining.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
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
        override fun getErrorMessage(): String = "An error occurred while loading.\nPlease check your internet connection."
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
    var seen: Boolean,
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

    fun markErrorAsSeen() {
        _errorStatus.value = _errorStatus.value.copy(seen = true)
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

            if (refresh)
                _jasonItems.value = emptyList()
            when (newJasonItems) {
                is TenorRequestResult.Success -> {
                    _jasonItems.value += newJasonItems.gifs
                    _errorStatus.value.fetchError = FetchError.Ok
                }
                is TenorRequestResult.Empty -> {
                    _errorStatus.value.fetchError = FetchError.NoDataLeftError
                }
                is TenorRequestResult.Error -> {
                    if (newJasonItems.exception is HttpException)
                        _errorStatus.value.fetchError = FetchError.NetworkError
                    else
                        _errorStatus.value.fetchError = FetchError.UnexpectedError("Unexpected error occurred while loading")
                }
            }
            _isLoading.value = false
            _isRefreshing.value = false
            _errorStatus.value.seen = false
        }
    }
}
