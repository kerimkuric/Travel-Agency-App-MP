package com.example.travelagencyapp.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.travelagencyapp.R
import com.example.travelagencyapp.model.Trip

@Composable
fun FeaturedTripCard(
    trip: Trip,
    onOpenDetails: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Trip image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = trip.title, style = MaterialTheme.typography.titleMedium)
            Text(text = trip.destination)
            Text(text = "${trip.days} days - $${trip.pricePerPerson}/person")

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = onOpenDetails, modifier = Modifier.fillMaxWidth()) {
                Text("View details")
            }
            }
        }
    }
}

