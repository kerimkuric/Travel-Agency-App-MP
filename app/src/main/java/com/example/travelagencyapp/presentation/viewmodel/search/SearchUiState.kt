package com.example.travelagencyapp.presentation.viewmodel.search

import com.example.travelagencyapp.model.data.TripModel

sealed interface SearchUiState {
    data object Init : SearchUiState
    data object Loading : SearchUiState
    data class Success(
        val query: String,
        val trips: List<TripModel>,
    ) : SearchUiState
    data class Error(val message: String) : SearchUiState
}
