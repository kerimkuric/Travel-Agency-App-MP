package com.example.travelagencyapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.travelagencyapp.ui.screens.booking.BookingFormScreen
import com.example.travelagencyapp.ui.screens.details.TripDetailsScreen
import com.example.travelagencyapp.ui.screens.home.HomeScreen
import com.example.travelagencyapp.ui.screens.search.TripSearchScreen
import com.example.travelagencyapp.ui.screens.trips.TripsListScreen
import com.example.travelagencyapp.ui.state.TravelScreen
import com.example.travelagencyapp.viewmodel.TravelAgencyViewModel
import com.example.travelagencyapp.viewmodel.TravelEvent

@Composable
fun TravelAgencyApp(
    modifier: Modifier = Modifier,
    viewModel: TravelAgencyViewModel = remember { TravelAgencyViewModel() },
) {
    val uiState = viewModel.uiState.collectAsState().value

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        when (uiState.currentScreen) {
            TravelScreen.Home -> {
                HomeScreen(
                    uiState = uiState,
                    onGoTrips = {
                        viewModel.onEvent(TravelEvent.Navigate(TravelScreen.Trips))
                    },
                    onGoSearch = {
                        viewModel.onEvent(TravelEvent.Navigate(TravelScreen.Search))
                    },
                    onOpenTripDetails = { tripId ->
                        viewModel.onEvent(TravelEvent.SelectTripForDetails(tripId))
                    }
                )
            }

            TravelScreen.Trips -> {
                TripsListScreen(
                    uiState = uiState,
                    onOpenTripDetails = { tripId ->
                        viewModel.onEvent(TravelEvent.SelectTripForDetails(tripId))
                    },
                    onClearTrips = { viewModel.onEvent(TravelEvent.ClearTrips) },
                    onResetTrips = { viewModel.onEvent(TravelEvent.ResetTrips) },
                    onGoHome = { viewModel.onEvent(TravelEvent.Navigate(TravelScreen.Home)) },
                    onGoSearch = { viewModel.onEvent(TravelEvent.Navigate(TravelScreen.Search)) },
                    onChangeSort = { sortOption ->
                        viewModel.onEvent(TravelEvent.ChangeSort(sortOption))
                    }
                )
            }

            TravelScreen.Details -> {
                TripDetailsScreen(
                    uiState = uiState,
                    onBackToTrips = { viewModel.onEvent(TravelEvent.Navigate(TravelScreen.Trips)) },
                    onBookTrip = { viewModel.onEvent(TravelEvent.NavigateToBooking) }
                )
            }

            TravelScreen.Booking -> {
                BookingFormScreen(
                    uiState = uiState,
                    onNameChanged = { viewModel.onEvent(TravelEvent.BookingNameChanged(it)) },
                    onEmailChanged = { viewModel.onEvent(TravelEvent.BookingEmailChanged(it)) },
                    onSubmit = { viewModel.onEvent(TravelEvent.BookingSubmitClicked) },
                    onCancel = { viewModel.onEvent(TravelEvent.BookingCancel) },
                    onGoTrips = { viewModel.onEvent(TravelEvent.Navigate(TravelScreen.Trips)) },
                )
            }

            TravelScreen.Search -> {
                TripSearchScreen(
                    uiState = uiState,
                    onSearchQueryChanged = { viewModel.onEvent(TravelEvent.SearchQueryChanged(it)) },
                    onClearSearch = { viewModel.onEvent(TravelEvent.SearchClear) },
                    onOpenTripDetails = { tripId ->
                        viewModel.onEvent(TravelEvent.SelectTripForDetails(tripId))
                    },
                    onBackToTrips = { viewModel.onEvent(TravelEvent.Navigate(TravelScreen.Trips)) },
                )
            }
        }
    }
}

