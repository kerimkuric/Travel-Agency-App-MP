package com.example.travelagencyapp.ui.state

import com.example.travelagencyapp.model.Trip

enum class TravelScreen {
    Home,
    Trips,
    Details,
    Booking,
    Search,
}

enum class TripSortOption {
    ByName,
    ByDays,
}

data class TravelUiState(
    val currentScreen: TravelScreen = TravelScreen.Home,
    val trips: List<Trip> = emptyList(),
    val selectedTripId: String? = null,
    val sortOption: TripSortOption = TripSortOption.ByName,
    val searchQuery: String = "",
    // Booking form state
    val bookingName: String = "",
    val bookingEmail: String = "",
    val bookingTouched: Boolean = false,
    val bookingNameError: String? = null,
    val bookingEmailError: String? = null,
    val bookingIsFormValid: Boolean = false,
    val bookingSubmitResult: String? = null,
) {
    val selectedTrip: Trip? = trips.firstOrNull { it.id == selectedTripId }
}

