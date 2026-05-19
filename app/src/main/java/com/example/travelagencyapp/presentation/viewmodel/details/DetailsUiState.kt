package com.example.travelagencyapp.presentation.viewmodel.details

import com.example.travelagencyapp.model.data.TripModel

sealed interface DetailsUiState {
    data object Init : DetailsUiState
    data object Loading : DetailsUiState
    data class Success(
        val trip: TripModel,
        val source: String,
    ) : DetailsUiState
    data class Error(val message: String) : DetailsUiState
}
