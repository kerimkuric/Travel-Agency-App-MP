package com.example.travelagencyapp.presentation.viewmodel.home

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
class HomeViewModel @Inject constructor(
    private val tripRepository: TripRepository,
    private val destinationRepository: DestinationRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Init)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val selectedDestination = MutableStateFlow("All")

    init {
        load()
    }

    fun onDestinationSelected(destination: String) {
        selectedDestination.value = destination
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                tripRepository.ensureSeeded()
                destinationRepository.ensureSeeded()

                combine(
                    tripRepository.observeAllTrips(),
                    destinationRepository.observeDestinationNames(),
                    selectedDestination,
                ) { trips, destinations, selected ->
                    val featured = filterTrips(trips, selected).take(5)
                    HomeUiState.Success(
                        featuredTrips = featured,
                        destinations = destinations,
                        selectedDestination = selected,
                    )
                }.collect { state ->
                    _uiState.value = state
                }
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Failed to load home data")
            }
        }
    }

    private fun filterTrips(trips: List<TripModel>, destination: String): List<TripModel> {
        if (destination == "All") return trips
        return trips.filter { it.destination == destination }
    }
}
