package com.example.travelagencyapp.ui.screens.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
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

@Composable
fun TripDetailsCard(
    tripTitle: String,
    destination: String,
    days: Int,
    pricePerPerson: Int,
    onBookTrip: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Trip image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
            )

            Text(text = tripTitle, style = MaterialTheme.typography.titleMedium)
            Text(text = destination)
            Text(text = "${days} days - $${pricePerPerson}/person")

            Button(
                onClick = onBookTrip,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text("Book this trip")
            }
        }
    }
}

