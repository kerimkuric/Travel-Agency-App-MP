package com.example.travelagencyapp.presentation.viewmodel.trips

import com.example.travelagencyapp.model.data.TripModel

sealed interface TripsUiState {
    data object Init : TripsUiState
    data object Loading : TripsUiState
    data class Success(
        val trips: List<TripModel>,
        val destinations: List<String>,
        val selectedDestination: String,
    ) : TripsUiState
    data class Error(val message: String) : TripsUiState
}
