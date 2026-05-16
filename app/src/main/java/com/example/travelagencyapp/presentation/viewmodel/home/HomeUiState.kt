package com.example.travelagencyapp.presentation.viewmodel.home

import com.example.travelagencyapp.model.data.TripModel

sealed interface HomeUiState {
    data object Init : HomeUiState
    data object Loading : HomeUiState
    data class Success(
        val featuredTrips: List<TripModel>,
        val destinations: List<String>,
        val selectedDestination: String,
    ) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
