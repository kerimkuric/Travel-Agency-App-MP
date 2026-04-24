package com.example.travelagencyapp.presentation.viewmodel

import com.example.travelagencyapp.model.data.HardcodedData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TravelViewModel {
    private val _uiState = MutableStateFlow(TravelUiState(trips = HardcodedData.trips))
    val uiState: StateFlow<TravelUiState> = _uiState.asStateFlow()

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun updateDestinationFilter(destination: String) {
        _uiState.value = _uiState.value.copy(selectedDestination = destination)
    }

    fun updateBookingName(value: String) {
        _uiState.value = _uiState.value.copy(bookingName = value, bookingResult = null)
    }

    fun updateBookingEmail(value: String) {
        _uiState.value = _uiState.value.copy(bookingEmail = value, bookingResult = null)
    }

    fun setBookingSource(source: String) {
        _uiState.value = _uiState.value.copy(bookingSource = source)
    }

    fun submitBooking() {
        if (!_uiState.value.isBookingValid) return
        _uiState.value = _uiState.value.copy(
            bookingResult = "Booking confirmed for ${_uiState.value.bookingName.trim()}."
        )
    }

    fun resetBooking() {
        _uiState.value = _uiState.value.copy(
            bookingName = "",
            bookingEmail = "",
            bookingResult = null,
            bookingSource = "",
        )
    }
}

