package com.example.travelagencyapp.ui.screens.trips

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.travelagencyapp.ui.screens.trips.components.EmptyTripsView
import com.example.travelagencyapp.ui.screens.trips.components.TripListItem
import com.example.travelagencyapp.ui.state.TripSortOption
import com.example.travelagencyapp.ui.state.TravelUiState

@Composable
fun TripsListScreen(
    uiState: TravelUiState,
    onOpenTripDetails: (String) -> Unit,
    onClearTrips: () -> Unit,
    onResetTrips: () -> Unit,
    onGoHome: () -> Unit,
    onGoSearch: () -> Unit,
    onChangeSort: (TripSortOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tripsSorted = when (uiState.sortOption) {
        TripSortOption.ByName -> uiState.trips.sortedBy { it.title }
        TripSortOption.ByDays -> uiState.trips.sortedBy { it.days }
    }

    var sortExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(text = "Trips", style = MaterialTheme.typography.titleLarge)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onGoHome) {
                Text("Home")
            }
            Button(onClick = onGoSearch) {
                Text("Search")
            }
        }

        // Menu: choose sort option
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Sort")
                OutlinedButton(onClick = { sortExpanded = true }) {
                    Text(
                        text = when (uiState.sortOption) {
                            TripSortOption.ByName -> "By name"
                            TripSortOption.ByDays -> "By days"
                        }
                    )
                }

                DropdownMenu(
                    expanded = sortExpanded,
                    onDismissRequest = { sortExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("By name") },
                        onClick = {
                            sortExpanded = false
                            onChangeSort(TripSortOption.ByName)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("By days") },
                        onClick = {
                            sortExpanded = false
                            onChangeSort(TripSortOption.ByDays)
                        }
                    )
                }
            }
        }

        // Edge case: empty list state
        if (tripsSorted.isEmpty()) {
            EmptyTripsView(
                onReset = onResetTrips,
                onGoHome = onGoHome,
            )
        } else {
            // List
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(tripsSorted, key = { it.id }) { trip ->
                    TripListItem(
                        trip = trip,
                        onClick = { onOpenTripDetails(trip.id) }
                    )
                }
            }
        }

        // Small dev/testing buttons: simulate empty list
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = onClearTrips, modifier = Modifier.weight(1f)) {
                Text("Clear list")
            }
            OutlinedButton(onClick = onResetTrips, modifier = Modifier.weight(1f)) {
                Text("Restore sample")
            }
        }
    }
}

