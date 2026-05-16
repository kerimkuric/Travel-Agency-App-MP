package com.example.travelagencyapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.travelagencyapp.model.data.TripModel
import com.example.travelagencyapp.presentation.ui.components.DestinationFilterRow
import com.example.travelagencyapp.presentation.ui.components.EmptyState
import com.example.travelagencyapp.presentation.ui.components.TripCard

@Composable
fun TripsScreen(
    trips: List<TripModel>,
    destinations: List<String>,
    selectedDestination: String,
    onDestinationSelected: (String) -> Unit,
    onOpenTrip: (String) -> Unit,
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
        Button(onClick = onGoSearch, modifier = Modifier.fillMaxWidth()) { Text("Search screen") }
        Button(onClick = onGoHome, modifier = Modifier.fillMaxWidth()) { Text("Back home") }

        if (trips.isEmpty()) {
            EmptyState(
                title = "No items available",
                message = "Change filters or try search.",
            )
        } else {
            // LazyColumn #1
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(items = trips, key = { it.id }, contentType = { "trip_card" }) { trip ->
                    TripCard(
                        trip = trip,
                        onClick = { onOpenTrip(trip.id) }
                    )
                }
            }
        }
    }
}

