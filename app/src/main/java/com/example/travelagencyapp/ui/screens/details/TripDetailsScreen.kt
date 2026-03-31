package com.example.travelagencyapp.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.travelagencyapp.ui.screens.details.components.TripDetailsCard
import com.example.travelagencyapp.ui.state.TravelUiState

@Composable
fun TripDetailsScreen(
    uiState: TravelUiState,
    onBackToTrips: () -> Unit,
    onBookTrip: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val trip = uiState.selectedTrip

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(text = "Trip details", style = MaterialTheme.typography.titleLarge)

        if (trip == null) {
            Text("Select a trip first.")
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = onBackToTrips, modifier = Modifier.fillMaxWidth()) {
                Text("Back to trips")
            }
        } else {
            TripDetailsCard(
                tripTitle = trip.title,
                destination = trip.destination,
                days = trip.days,
                pricePerPerson = trip.pricePerPerson,
                onBookTrip = onBookTrip
            )

            Button(onClick = onBackToTrips, modifier = Modifier.fillMaxWidth()) {
                Text("Back to trips")
            }
        }
    }
}

