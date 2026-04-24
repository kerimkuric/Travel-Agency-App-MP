package com.example.travelagencyapp.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.travelagencyapp.model.data.TripModel

@Composable
fun TripCard(
    trip: TripModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = trip.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "${trip.destination} - ${trip.days} days")
            Text(text = "$${trip.pricePerPerson}/person")
            Text(text = trip.category, style = MaterialTheme.typography.bodySmall)
        }
    }
}

