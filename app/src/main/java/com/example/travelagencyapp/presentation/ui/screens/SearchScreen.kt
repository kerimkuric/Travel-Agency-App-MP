package com.example.travelagencyapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.travelagencyapp.presentation.ui.components.EmptyState
import com.example.travelagencyapp.presentation.ui.components.ErrorView
import com.example.travelagencyapp.presentation.ui.components.LoadingView
import com.example.travelagencyapp.presentation.ui.components.TripCard
import com.example.travelagencyapp.presentation.viewmodel.search.SearchUiState
import com.example.travelagencyapp.presentation.viewmodel.search.SearchViewModel

@Composable
fun SearchScreen(
    onOpenTrip: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        SearchUiState.Init, SearchUiState.Loading -> LoadingView(modifier)
        is SearchUiState.Error -> ErrorView(message = state.message, modifier = modifier)
        is SearchUiState.Success -> SearchScreenContent(
            query = state.query,
            trips = state.trips,
            onQueryChange = viewModel::onQueryChange,
            onClearSearch = viewModel::clearSearch,
            onOpenTrip = onOpenTrip,
            onBack = onBack,
            modifier = modifier,
        )
    }
}

@Composable
fun SearchScreenContent(
    query: String,
    trips: List<TripModel>,
    onQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    onOpenTrip: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Search", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            label = { Text("Search by title or destination") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Back") }
        Button(onClick = onClearSearch, modifier = Modifier.fillMaxWidth()) { Text("Clear") }

        if (trips.isEmpty()) {
            EmptyState(
                title = "No items available",
                message = "Try another search keyword.",
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(trips, key = { it.id }, contentType = { "search_trip_card" }) { trip ->
                    TripCard(trip = trip, onClick = { onOpenTrip(trip.id) })
                }
            }
        }
    }
}
