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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.travelagencyapp.model.data.TripModel
import com.example.travelagencyapp.presentation.ui.components.ErrorView
import com.example.travelagencyapp.presentation.ui.components.LoadingView
import com.example.travelagencyapp.presentation.viewmodel.details.DetailsUiState
import com.example.travelagencyapp.presentation.viewmodel.details.DetailsViewModel

@Composable
fun DetailsScreen(
    onBack: () -> Unit,
    onBook: (tripId: String, source: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        DetailsUiState.Init, DetailsUiState.Loading -> LoadingView(modifier)
        is DetailsUiState.Error -> ErrorView(message = state.message, modifier = modifier)
        is DetailsUiState.Success -> DetailsScreenContent(
            trip = state.trip,
            onBack = onBack,
            onBook = { onBook(state.trip.id, state.source) },
            modifier = modifier,
        )
    }
}

@Composable
fun DetailsScreenContent(
    trip: TripModel,
    onBack: () -> Unit,
    onBook: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Trip details", style = MaterialTheme.typography.headlineSmall)
        Text(trip.title, style = MaterialTheme.typography.titleLarge)
        Text("${trip.destination} - ${trip.days} days")
        Text("$${trip.pricePerPerson}/person")
        Text("Category: ${trip.category}")

        Button(onClick = onBook, modifier = Modifier.fillMaxWidth()) {
            Text("Book now")
        }
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Back")
        }
    }
}
