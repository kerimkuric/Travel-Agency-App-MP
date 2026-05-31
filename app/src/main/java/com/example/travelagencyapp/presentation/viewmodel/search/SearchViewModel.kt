package com.example.travelagencyapp.presentation.viewmodel.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelagencyapp.model.data.TripModel
import com.example.travelagencyapp.model.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val tripRepository: TripRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Init)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val searchQuery = MutableStateFlow("")

    init {
        load()
    }

    fun onQueryChange(query: String) {
        searchQuery.value = query
    }

    fun clearSearch() {
        searchQuery.value = ""
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            try {
                tripRepository.ensureLocalCatalog()

                combine(
                    tripRepository.observeAllTrips(),
                    searchQuery,
                ) { trips, query ->
                    SearchUiState.Success(
                        query = query,
                        trips = filterByQuery(trips, query),
                    )
                }.collect { state ->
                    _uiState.value = state
                }
            } catch (e: Exception) {
                _uiState.value = SearchUiState.Error(e.message ?: "Failed to load search")
            }
        }
    }

    private fun filterByQuery(trips: List<TripModel>, query: String): List<TripModel> {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) return trips
        return trips.filter {
            it.title.contains(trimmed, ignoreCase = true) ||
                it.destination.contains(trimmed, ignoreCase = true)
        }
    }
}
