package com.example.travelagencyapp.presentation.viewmodel.trips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelagencyapp.model.data.TripModel
import com.example.travelagencyapp.model.repository.DestinationRepository
import com.example.travelagencyapp.model.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@HiltViewModel
class TripsViewModel @Inject constructor(
    private val tripRepository: TripRepository,
    private val destinationRepository: DestinationRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<TripsUiState>(TripsUiState.Init)
    val uiState: StateFlow<TripsUiState> = _uiState.asStateFlow()

    private val selectedDestination = MutableStateFlow("All")

    init {
        load()
    }

    fun onDestinationSelected(destination: String) {
        selectedDestination.value = destination
    }

    fun deleteTrip(tripId: String) {
        viewModelScope.launch {
            try {
                tripRepository.deleteTrip(tripId.toLong())
            } catch (e: Exception) {
                _uiState.value = TripsUiState.Error(e.message ?: "Failed to delete trip")
            }
        }
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = TripsUiState.Loading
            try {
                tripRepository.ensureSeeded()
                destinationRepository.ensureSeeded()

                combine(
                    tripRepository.observeAllTrips(),
                    destinationRepository.observeDestinationNames(),
                    selectedDestination,
                ) { trips, destinations, selected ->
                    TripsUiState.Success(
                        trips = filterTrips(trips, selected),
                        destinations = destinations,
                        selectedDestination = selected,
                    )
                }.collect { state ->
                    _uiState.value = state
                }
            } catch (e: Exception) {
                _uiState.value = TripsUiState.Error(e.message ?: "Failed to load trips")
            }
        }
    }

    private fun filterTrips(trips: List<TripModel>, destination: String): List<TripModel> {
        if (destination == "All") return trips
        return trips.filter { it.destination == destination }
    }
}
