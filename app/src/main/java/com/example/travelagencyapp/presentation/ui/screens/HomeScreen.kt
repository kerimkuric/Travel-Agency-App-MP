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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.travelagencyapp.model.data.TripModel
import com.example.travelagencyapp.presentation.ui.components.DestinationFilterRow
import com.example.travelagencyapp.presentation.ui.components.ErrorView
import com.example.travelagencyapp.presentation.ui.components.FeaturedTripTile
import com.example.travelagencyapp.presentation.ui.components.LoadingView
import com.example.travelagencyapp.presentation.viewmodel.home.HomeUiState
import com.example.travelagencyapp.presentation.viewmodel.home.HomeViewModel

@Composable
fun HomeScreen(
    onOpenTrip: (String) -> Unit,
    onGoTrips: () -> Unit,
    onGoSearch: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        HomeUiState.Init, HomeUiState.Loading -> LoadingView(modifier)
        is HomeUiState.Error -> ErrorView(message = state.message, modifier = modifier)
        is HomeUiState.Success -> HomeScreenContent(
            featuredTrips = state.featuredTrips,
            destinations = state.destinations,
            selectedDestination = state.selectedDestination,
            onDestinationSelected = viewModel::onDestinationSelected,
            onOpenTrip = onOpenTrip,
            onGoTrips = onGoTrips,
            onGoSearch = onGoSearch,
            modifier = modifier,
        )
    }
}

@Composable
fun HomeScreenContent(
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
        Text(
            text = "Discover your next adventure",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        DestinationFilterRow(
            destinations = destinations,
            selected = selectedDestination,
            onSelected = onDestinationSelected,
        )

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
            Text("Browse all trips")
        }
        Button(onClick = onGoSearch, modifier = Modifier.fillMaxWidth()) {
            Text("Search trips")
        }
    }
}
