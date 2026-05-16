package com.example.travelagencyapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.travelagencyapp.model.data.TripModel
import com.example.travelagencyapp.presentation.ui.components.DestinationFilterRow
import com.example.travelagencyapp.presentation.ui.components.EmptyState
import com.example.travelagencyapp.presentation.ui.components.ErrorView
import com.example.travelagencyapp.presentation.ui.components.LoadingView
import com.example.travelagencyapp.presentation.ui.components.TripCard
import com.example.travelagencyapp.presentation.viewmodel.trips.TripsUiState
import com.example.travelagencyapp.presentation.viewmodel.trips.TripsViewModel

@Composable
fun TripsScreen(
    onOpenTrip: (String) -> Unit,
    onGoHome: () -> Unit,
    onGoSearch: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TripsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        TripsUiState.Init, TripsUiState.Loading -> LoadingView(modifier)
        is TripsUiState.Error -> ErrorView(message = state.message, modifier = modifier)
        is TripsUiState.Success -> TripsScreenContent(
            trips = state.trips,
            destinations = state.destinations,
            selectedDestination = state.selectedDestination,
            onDestinationSelected = viewModel::onDestinationSelected,
            onOpenTrip = onOpenTrip,
            onDeleteTrip = viewModel::deleteTrip,
            onGoHome = onGoHome,
            onGoSearch = onGoSearch,
            modifier = modifier,
        )
    }
}

@Composable
fun TripsScreenContent(
    trips: List<TripModel>,
    destinations: List<String>,
    selectedDestination: String,
    onDestinationSelected: (String) -> Unit,
    onOpenTrip: (String) -> Unit,
    onDeleteTrip: (String) -> Unit,
    onGoHome: () -> Unit,
    onGoSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Trips", style = MaterialTheme.typography.headlineSmall)
        DestinationFilterRow(
            destinations = destinations,
            selected = selectedDestination,
            onSelected = onDestinationSelected,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onGoSearch, modifier = Modifier.weight(1f)) { Text("Search") }
            Button(onClick = onGoHome, modifier = Modifier.weight(1f)) { Text("Home") }
        }

        if (trips.isEmpty()) {
            EmptyState(
                title = "No items available",
                message = "No trips match this filter.",
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(trips, key = { it.id }, contentType = { "trip_card" }) { trip ->
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        TripCard(trip = trip, onClick = { onOpenTrip(trip.id) })
                        OutlinedButton(
                            onClick = { onDeleteTrip(trip.id) },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text("Delete trip")
                        }
                    }
                }
            }
        }
    }
}
