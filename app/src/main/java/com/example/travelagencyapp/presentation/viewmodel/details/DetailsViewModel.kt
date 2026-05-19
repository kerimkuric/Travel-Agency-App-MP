package com.example.travelagencyapp.presentation.viewmodel.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelagencyapp.model.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository,
) : ViewModel() {

    private val tripId: Long = savedStateHandle.get<String>("tripId")?.toLongOrNull() ?: -1L
    private val source: String = savedStateHandle.get<String>("source").orEmpty()

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Init)
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = DetailsUiState.Loading
            try {
                tripRepository.ensureSeeded()
                if (tripId <= 0L) {
                    _uiState.value = DetailsUiState.Error("Invalid trip id")
                    return@launch
                }
                val trip = tripRepository.observeTrip(tripId).first()
                if (trip == null) {
                    _uiState.value = DetailsUiState.Error("Trip not found")
                } else {
                    _uiState.value = DetailsUiState.Success(trip = trip, source = source)
                }
            } catch (e: Exception) {
                _uiState.value = DetailsUiState.Error(e.message ?: "Trip not found")
            }
        }
    }
}
