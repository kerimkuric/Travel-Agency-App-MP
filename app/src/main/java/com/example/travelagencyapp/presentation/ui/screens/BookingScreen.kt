package com.example.travelagencyapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.travelagencyapp.model.data.TripModel

@Composable
fun BookingScreen(
    trip: TripModel?,
    source: String,
    bookingName: String,
    bookingEmail: String,
    isBookingValid: Boolean,
    bookingResult: String?,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Booking", style = MaterialTheme.typography.headlineSmall)
        Text("Opened from: $source")

        if (trip == null) {
            Text("Trip not found.")
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Back") }
            return
        }

        Text("Trip: ${trip.title}")

        OutlinedTextField(
            value = bookingName,
            onValueChange = onNameChange,
            label = { Text("Full name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = bookingEmail,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        Button(
            onClick = onSubmit,
            enabled = isBookingValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit booking")
        }
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Back")
        }

        bookingResult?.let {
            Text(it, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

