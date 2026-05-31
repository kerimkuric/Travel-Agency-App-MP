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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.travelagencyapp.model.data.TripModel
import com.example.travelagencyapp.presentation.ui.components.ErrorView
import com.example.travelagencyapp.presentation.ui.components.LoadingView
import com.example.travelagencyapp.presentation.viewmodel.booking.BookingUiState
import com.example.travelagencyapp.presentation.viewmodel.booking.BookingViewModel

@Composable
fun BookingScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BookingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        BookingUiState.Init, BookingUiState.Loading -> LoadingView(modifier)
        is BookingUiState.Error -> ErrorView(message = state.message, modifier = modifier)
        is BookingUiState.Success -> BookingScreenContent(
            trip = state.trip,
            bookingName = state.bookingName,
            bookingEmail = state.bookingEmail,
            isFormValid = state.isFormValid,
            submitResult = state.submitResult,
            onNameChange = viewModel::onNameChange,
            onEmailChange = viewModel::onEmailChange,
            onSubmit = viewModel::submitBooking,
            onBack = {
                viewModel.resetSubmitMessage()
                onBack()
            },
            modifier = modifier,
        )
    }
}

@Composable
fun BookingScreenContent(
    trip: TripModel,
    bookingName: String,
    bookingEmail: String,
    isFormValid: Boolean,
    submitResult: String?,
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
        Text(trip.title, style = MaterialTheme.typography.titleMedium)

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
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Submit booking")
        }
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Back")
        }

        submitResult?.let {
            Text(it, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
