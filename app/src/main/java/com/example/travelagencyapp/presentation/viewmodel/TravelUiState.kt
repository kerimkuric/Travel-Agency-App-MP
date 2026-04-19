package com.example.travelagencyapp.presentation.viewmodel

import com.example.travelagencyapp.model.data.TripModel

data class TravelUiState(
    val trips: List<TripModel> = emptyList(),
    val selectedDestination: String = "All",
    val searchQuery: String = "",
    val bookingName: String = "",
    val bookingEmail: String = "",
    val bookingResult: String? = null,
    val bookingSource: String = "",
) {
    // Derived state #1
    val destinationFilters: List<String>
        get() = listOf("All") + trips.map { it.destination }.distinct()

    // Derived state #2
    val filteredTrips: List<TripModel>
        get() {
            val byDestination = if (selectedDestination == "All") {
                trips
            } else {
                trips.filter { it.destination == selectedDestination }
            }

            val query = searchQuery.trim()
            if (query.isEmpty()) return byDestination

            return byDestination.filter {
                it.title.contains(query, ignoreCase = true) ||
                    it.destination.contains(query, ignoreCase = true)
            }
        }

    // Derived state #3
    val featuredTrips: List<TripModel>
        get() = trips.take(5)

    // Derived state #4
    val isBookingValid: Boolean
        get() = bookingName.trim().length >= 3 &&
            bookingEmail.contains("@") &&
            bookingEmail.contains(".")
}

