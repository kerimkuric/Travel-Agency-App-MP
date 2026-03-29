package com.example.travelagencyapp.viewmodel

import com.example.travelagencyapp.model.Trip
import com.example.travelagencyapp.ui.state.TripSortOption
import com.example.travelagencyapp.ui.state.TravelScreen
import com.example.travelagencyapp.ui.state.TravelUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TravelAgencyViewModel {

    private val sampleTrips: List<Trip> = listOf(
        Trip(
            id = "istanbul-3",
            title = "Istanbul Highlights",
            destination = "Istanbul",
            days = 3,
            pricePerPerson = 450
        ),
        Trip(
            id = "antalya-5",
            title = "Antalya Coast & Culture",
            destination = "Antalya",
            days = 5,
            pricePerPerson = 720
        ),
        Trip(
            id = "paris-4",
            title = "Paris City Break",
            destination = "Paris",
            days = 4,
            pricePerPerson = 980
        ),
    )

    private val _uiState = MutableStateFlow(
        TravelUiState(
            trips = sampleTrips,
            selectedTripId = sampleTrips.firstOrNull()?.id,
        )
    )

    val uiState: StateFlow<TravelUiState> = _uiState.asStateFlow()

    fun onEvent(event: TravelEvent) {
        when (event) {
            is TravelEvent.Navigate -> navigateTo(event.screen)
            is TravelEvent.SelectTripForDetails -> selectTripForDetails(event.tripId)
            TravelEvent.NavigateToBooking -> navigateToBooking()
            TravelEvent.ClearTrips -> clearTrips()
            TravelEvent.ResetTrips -> resetTrips()
            is TravelEvent.ChangeSort -> changeSort(event.sortOption)

            is TravelEvent.BookingNameChanged -> updateBookingName(event.value)
            is TravelEvent.BookingEmailChanged -> updateBookingEmail(event.value)
            TravelEvent.BookingSubmitClicked -> submitBooking()
            TravelEvent.BookingCancel -> cancelBooking()

            is TravelEvent.SearchQueryChanged -> updateSearchQuery(event.value)
            TravelEvent.SearchClear -> clearSearch()
        }
    }

    private fun navigateTo(screen: TravelScreen) {
        _uiState.value = _uiState.value.copy(
            currentScreen = screen
        )
    }

    private fun selectTripForDetails(tripId: String) {
        _uiState.value = _uiState.value.copy(
            selectedTripId = tripId,
            currentScreen = TravelScreen.Details,
        )
    }

    private fun navigateToBooking() {
        val emptyState = _uiState.value.copy(
            bookingName = "",
            bookingEmail = ""
        )
        val (nameError, emailError, _) = validateBooking(emptyState)
        _uiState.value = _uiState.value.copy(
            currentScreen = TravelScreen.Booking,
            bookingTouched = false,
            bookingSubmitResult = null,
            bookingName = "",
            bookingEmail = "",
            bookingNameError = nameError,
            bookingEmailError = emailError,
            bookingIsFormValid = false,
        )
    }

    private fun clearTrips() {
        _uiState.value = _uiState.value.copy(
            trips = emptyList(),
            selectedTripId = null,
            currentScreen = TravelScreen.Trips
        )
    }

    private fun resetTrips() {
        _uiState.value = _uiState.value.copy(
            trips = sampleTrips,
            selectedTripId = sampleTrips.firstOrNull()?.id,
            currentScreen = TravelScreen.Trips
        )
    }

    private fun changeSort(sortOption: TripSortOption) {
        _uiState.value = _uiState.value.copy(sortOption = sortOption)
    }

    private fun updateBookingName(value: String) {
        val newState = _uiState.value.copy(bookingName = value)
        val (nameError, emailError, isValid) = validateBooking(newState)
        _uiState.value = newState.copy(
            bookingNameError = nameError,
            bookingEmailError = emailError,
            bookingIsFormValid = isValid,
            bookingSubmitResult = null,
        )
    }

    private fun updateBookingEmail(value: String) {
        val newState = _uiState.value.copy(bookingEmail = value)
        val (nameError, emailError, isValid) = validateBooking(newState)
        _uiState.value = newState.copy(
            bookingNameError = nameError,
            bookingEmailError = emailError,
            bookingIsFormValid = isValid,
            bookingSubmitResult = null,
        )
    }

    private fun submitBooking() {
        val touchedState = _uiState.value.copy(bookingTouched = true)
        val (nameError, emailError, isValid) = validateBooking(touchedState)
        if (!isValid) {
            _uiState.value = touchedState.copy(
                bookingNameError = nameError,
                bookingEmailError = emailError,
                bookingIsFormValid = false,
                bookingSubmitResult = null,
            )
            return
        }

        val safeName = touchedState.bookingName.trim()
        _uiState.value = touchedState.copy(
            bookingNameError = null,
            bookingEmailError = null,
            bookingIsFormValid = true,
            bookingSubmitResult = "Booking confirmed for $safeName.",
        )
    }

    private fun cancelBooking() {
        _uiState.value = _uiState.value.copy(
            currentScreen = TravelScreen.Trips,
            bookingTouched = false,
            bookingSubmitResult = null,
        )
    }

    private fun updateSearchQuery(value: String) {
        _uiState.value = _uiState.value.copy(searchQuery = value)
    }

    private fun clearSearch() {
        _uiState.value = _uiState.value.copy(searchQuery = "")
    }

    private fun validateBooking(state: TravelUiState): Triple<String?, String?, Boolean> {
        val nameError = when {
            state.bookingName.isBlank() -> "Name is required"
            state.bookingName.trim().length < 3 -> "Name must be at least 3 characters"
            else -> null
        }

        val emailError = when {
            state.bookingEmail.isBlank() -> "Email is required"
            !state.bookingEmail.contains("@") -> "Email must contain @"
            !state.bookingEmail.contains(".") -> "Email must contain a dot"
            else -> null
        }

        val isValid = nameError == null && emailError == null
        return Triple(nameError, emailError, isValid)
    }
}

sealed interface TravelEvent {
    data class Navigate(val screen: TravelScreen) : TravelEvent
    data class SelectTripForDetails(val tripId: String) : TravelEvent

    data object NavigateToBooking : TravelEvent
    data object ClearTrips : TravelEvent
    data object ResetTrips : TravelEvent
    data class ChangeSort(val sortOption: TripSortOption) : TravelEvent

    data class BookingNameChanged(val value: String) : TravelEvent
    data class BookingEmailChanged(val value: String) : TravelEvent
    data object BookingSubmitClicked : TravelEvent
    data object BookingCancel : TravelEvent

    data class SearchQueryChanged(val value: String) : TravelEvent
    data object SearchClear : TravelEvent
}

