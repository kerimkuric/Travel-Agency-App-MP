package com.example.travelagencyapp.presentation.viewmodel.booking

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelagencyapp.model.data.TripModel
import com.example.travelagencyapp.model.repository.BookingRepository
import com.example.travelagencyapp.model.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class BookingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository,
    private val bookingRepository: BookingRepository,
) : ViewModel() {

    private val tripId: Long = savedStateHandle.get<String>("tripId")?.toLongOrNull() ?: -1L
    private val source: String = savedStateHandle.get<String>("source").orEmpty()

    private val bookingName = MutableStateFlow("")
    private val bookingEmail = MutableStateFlow("")
    private val submitResult = MutableStateFlow<String?>(null)

    private val _uiState = MutableStateFlow<BookingUiState>(BookingUiState.Init)
    val uiState: StateFlow<BookingUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun onNameChange(value: String) {
        bookingName.value = value
        submitResult.value = null
        publishFormState()
    }

    fun onEmailChange(value: String) {
        bookingEmail.value = value
        submitResult.value = null
        publishFormState()
    }

    fun submitBooking() {
        val name = bookingName.value.trim()
        val email = bookingEmail.value.trim()
        if (!isValid(name, email)) return

        viewModelScope.launch {
            try {
                bookingRepository.createBooking(
                    tripId = tripId,
                    customerName = name,
                    customerEmail = email,
                )
                submitResult.value = "Booking confirmed for $name."
                publishFormState()
            } catch (e: Exception) {
                _uiState.value = BookingUiState.Error(e.message ?: "Booking failed")
            }
        }
    }

    fun resetSubmitMessage() {
        submitResult.value = null
        publishFormState()
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = BookingUiState.Loading
            try {
                tripRepository.ensureSeeded()
                if (tripId <= 0L) {
                    _uiState.value = BookingUiState.Error("Invalid trip id")
                    return@launch
                }
                val trip = tripRepository.observeTrip(tripId).first()
                if (trip == null) {
                    _uiState.value = BookingUiState.Error("Trip not found")
                } else {
                    publishFormState(trip)
                }
            } catch (e: Exception) {
                _uiState.value = BookingUiState.Error(e.message ?: "Trip not found")
            }
        }
    }

    private fun publishFormState(trip: TripModel? = null) {
        val current = _uiState.value
        val resolvedTrip = trip ?: (current as? BookingUiState.Success)?.trip
        if (resolvedTrip == null) return

        val name = bookingName.value
        val email = bookingEmail.value
        _uiState.value = BookingUiState.Success(
            trip = resolvedTrip,
            source = source,
            bookingName = name,
            bookingEmail = email,
            isFormValid = isValid(name, email),
            submitResult = submitResult.value,
        )
    }

    private fun isValid(name: String, email: String): Boolean {
        return name.trim().length >= 3 &&
            email.contains("@") &&
            email.contains(".")
    }
}
