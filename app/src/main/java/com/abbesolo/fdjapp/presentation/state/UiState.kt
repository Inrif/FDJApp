package com.abbesolo.fdjapp.presentation.state

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<out T>(val data: T) : UiState<T>
    data object Error : UiState<Nothing>
}