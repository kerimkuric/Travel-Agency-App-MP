package com.example.travelagencyapp.presentation.viewmodel.booking

import com.example.travelagencyapp.model.data.TripModel

sealed interface BookingUiState {
    data object Init : BookingUiState
    data object Loading : BookingUiState
    data class Success(
        val trip: TripModel,
        val source: String,
        val bookingName: String,
        val bookingEmail: String,
        val isFormValid: Boolean,
        val submitResult: String?,
    ) : BookingUiState
    data class Error(val message: String) : BookingUiState
}
