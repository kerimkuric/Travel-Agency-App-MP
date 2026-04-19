package com.example.travelagencyapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.travelagencyapp.model.data.TripModel

@Composable
fun DetailsScreen(
    trip: TripModel?,
    source: String,
    onBack: () -> Unit,
    onBook: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Details", style = MaterialTheme.typography.headlineSmall)
        Text("Opened from: $source")

        if (trip == null) {
            Text("Trip not found.")
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Back") }
            return
        }

        Text(trip.title, style = MaterialTheme.typography.titleLarge)
        Text("${trip.destination} - ${trip.days} days")
        Text("$${trip.pricePerPerson}/person")
        Text("Category: ${trip.category}")

        Button(onClick = { onBook(trip.id) }, modifier = Modifier.fillMaxWidth()) {
            Text("Book now")
        }
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Back")
        }
    }
}

