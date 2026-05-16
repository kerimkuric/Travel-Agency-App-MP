package com.example.travelagencyapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.travelagencyapp.model.data.TripModel
import com.example.travelagencyapp.presentation.ui.components.DestinationFilterRow
import com.example.travelagencyapp.presentation.ui.components.FeaturedTripTile

@Composable
fun HomeScreen(
    featuredTrips: List<TripModel>,
    destinations: List<String>,
    selectedDestination: String,
    onDestinationSelected: (String) -> Unit,
    onOpenTrip: (String) -> Unit,
    onGoTrips: () -> Unit,
    onGoSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Travel Agency", style = MaterialTheme.typography.headlineSmall)
        Text("Week 5-7 state, lists and navigation")

        // LazyRow #1
        DestinationFilterRow(
            destinations = destinations,
            selected = selectedDestination,
            onSelected = onDestinationSelected,
        )

        // LazyRow #2
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 4.dp),
        ) {
            items(featuredTrips, key = { it.id }, contentType = { "featured_trip" }) { trip ->
                FeaturedTripTile(
                    trip = trip,
                    onClick = { onOpenTrip(trip.id) },
                )
            }
        }

        Button(onClick = onGoTrips, modifier = Modifier.fillMaxWidth()) {
            Text("Open trips list")
        }
        Button(onClick = onGoSearch, modifier = Modifier.fillMaxWidth()) {
            Text("Open search")
        }
    }
}

