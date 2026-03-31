package com.example.travelagencyapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.travelagencyapp.ui.screens.home.components.FeaturedTripCard
import com.example.travelagencyapp.ui.screens.home.components.HomeHeader
import com.example.travelagencyapp.ui.state.TravelUiState
import com.example.travelagencyapp.model.Trip

@Composable
fun HomeScreen(
    uiState: TravelUiState,
    onGoTrips: () -> Unit,
    onGoSearch: () -> Unit,
    onOpenTripDetails: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val featuredTrip: Trip? = uiState.trips.firstOrNull()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        HomeHeader(
            title = "Travel Agency",
            onGoSearch = onGoSearch,
        )

        if (featuredTrip == null) {
            Text("No featured trips right now. Check back soon.")
        } else {
            FeaturedTripCard(
                trip = featuredTrip,
                onOpenDetails = { onOpenTripDetails(featuredTrip.id) }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onGoTrips, modifier = Modifier.fillMaxWidth()) {
            Text("Browse trips")
        }
    }
}

