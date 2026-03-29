package com.example.travelagencyapp.ui.screens.booking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.travelagencyapp.ui.screens.booking.components.BookingTextField
import com.example.travelagencyapp.ui.screens.booking.components.FormTitle
import com.example.travelagencyapp.ui.screens.booking.components.ValidationErrorText
import com.example.travelagencyapp.ui.state.TravelUiState

@Composable
fun BookingFormScreen(
    uiState: TravelUiState,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onSubmit: () -> Unit,
    onCancel: () -> Unit,
    onGoTrips: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val tripTitle = uiState.selectedTrip?.title
    val canSubmit = uiState.bookingIsFormValid

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        FormTitle(title = "Book a trip")

        if (tripTitle == null) {
            Text("No trip selected.")
            Button(onClick = onGoTrips, modifier = Modifier.fillMaxWidth()) {
                Text("Back to trips")
            }
        } else {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = "Selected trip:", style = MaterialTheme.typography.titleMedium)
                    Text(text = tripTitle)
                }
            }

            BookingTextField(
                label = "Full name",
                value = uiState.bookingName,
                onValueChange = onNameChanged,
                errorText = uiState.bookingNameError,
            )

            BookingTextField(
                label = "Email",
                value = uiState.bookingEmail,
                onValueChange = onEmailChanged,
                errorText = uiState.bookingEmailError,
            )

            if (!canSubmit) {
                ValidationErrorText(
                    text = "Fix the fields above to enable Submit."
                )
            }

            Button(
                onClick = onSubmit,
                enabled = canSubmit,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Submit booking")
            }

            Spacer(modifier = Modifier.padding(top = 6.dp))

            Button(
                onClick = {
                    onCancel()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel")
            }

            uiState.bookingSubmitResult?.let { result ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(text = "Result", style = MaterialTheme.typography.titleMedium)
                        Text(text = result)
                    }
                }
            }
        }
    }
}

