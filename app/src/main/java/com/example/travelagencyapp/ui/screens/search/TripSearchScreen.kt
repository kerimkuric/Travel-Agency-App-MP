package com.example.travelagencyapp.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.travelagencyapp.model.Trip
import com.example.travelagencyapp.ui.screens.search.components.NoResultsView
import com.example.travelagencyapp.ui.screens.search.components.SearchBar
import com.example.travelagencyapp.ui.screens.search.components.TripSearchResultItem
import com.example.travelagencyapp.ui.state.TravelUiState

@Composable
fun TripSearchScreen(
    uiState: TravelUiState,
    onSearchQueryChanged: (String) -> Unit,
    onClearSearch: () -> Unit,
    onOpenTripDetails: (String) -> Unit,
    onBackToTrips: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val query = uiState.searchQuery.trim()
    val tripsToShow: List<Trip> = if (query.isBlank()) {
        uiState.trips
    } else {
        uiState.trips.filter { trip ->
            trip.title.contains(query, ignoreCase = true) ||
                trip.destination.contains(query, ignoreCase = true)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(text = "Search trips", style = MaterialTheme.typography.titleLarge)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            Button(onClick = onBackToTrips, modifier = Modifier.weight(1f)) {
                Text("Back")
            }
            Button(onClick = onClearSearch, modifier = Modifier.weight(1f)) {
                Text("Clear")
            }
        }

        SearchBar(
            query = uiState.searchQuery,
            onQueryChange = onSearchQueryChanged,
        )

        if (tripsToShow.isEmpty()) {
            NoResultsView(onClear = onClearSearch)
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(tripsToShow, key = { it.id }) { trip ->
                    TripSearchResultItem(
                        trip = trip,
                        onClick = { onOpenTripDetails(trip.id) }
                    )
                }
            }
        }
    }
}

