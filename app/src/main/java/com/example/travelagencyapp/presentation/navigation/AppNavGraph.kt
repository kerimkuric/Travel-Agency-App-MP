package com.example.travelagencyapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.travelagencyapp.presentation.ui.screens.BookingScreen
import com.example.travelagencyapp.presentation.ui.screens.DetailsScreen
import com.example.travelagencyapp.presentation.ui.screens.HomeScreen
import com.example.travelagencyapp.presentation.ui.screens.SearchScreen
import com.example.travelagencyapp.presentation.ui.screens.TripsScreen
import com.example.travelagencyapp.presentation.viewmodel.TravelUiState

@Composable
fun AppNavGraph(
    uiState: TravelUiState,
    onSearchQueryChange: (String) -> Unit,
    onDestinationFilterChange: (String) -> Unit,
    onBookingNameChange: (String) -> Unit,
    onBookingEmailChange: (String) -> Unit,
    onSubmitBooking: () -> Unit,
    onSetBookingSource: (String) -> Unit,
    onResetBooking: () -> Unit,
    appNavController: androidx.navigation.NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = appNavController,
        startDestination = Screen.Home.route,
        modifier = modifier,
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                featuredTrips = uiState.featuredTrips,
                destinations = uiState.destinationFilters,
                selectedDestination = uiState.selectedDestination,
                onDestinationSelected = onDestinationFilterChange,
                onOpenTrip = { tripId ->
                    appNavController.navigate(Screen.Details.createRoute(tripId, "home"))
                },
                onGoTrips = { appNavController.navigate(Screen.Trips.route) },
                onGoSearch = { appNavController.navigate(Screen.Search.route) },
            )
        }

        composable(Screen.Trips.route) {
            TripsScreen(
                trips = uiState.filteredTrips,
                destinations = uiState.destinationFilters,
                selectedDestination = uiState.selectedDestination,
                onDestinationSelected = onDestinationFilterChange,
                onOpenTrip = { tripId ->
                    appNavController.navigate(Screen.Details.createRoute(tripId, "trips"))
                },
                onGoHome = { appNavController.navigateUp() },
                onGoSearch = { appNavController.navigate(Screen.Search.route) },
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(
                query = uiState.searchQuery,
                trips = uiState.filteredTrips,
                onQueryChange = onSearchQueryChange,
                onOpenTrip = { tripId ->
                    appNavController.navigate(Screen.Details.createRoute(tripId, "search"))
                },
                onBack = { appNavController.navigateUp() },
            )
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument("tripId") { type = NavType.StringType },
                navArgument("source") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId").orEmpty()
            val source = backStackEntry.arguments?.getString("source").orEmpty()
            val trip = uiState.trips.firstOrNull { it.id == tripId }
            DetailsScreen(
                trip = trip,
                source = source,
                onBack = { appNavController.navigateUp() },
                onBook = { id ->
                    onSetBookingSource(source)
                    appNavController.navigate(Screen.Booking.createRoute(id, source))
                },
            )
        }

        composable(
            route = Screen.Booking.route,
            arguments = listOf(
                navArgument("tripId") { type = NavType.StringType },
                navArgument("source") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId").orEmpty()
            val source = backStackEntry.arguments?.getString("source").orEmpty()
            val trip = uiState.trips.firstOrNull { it.id == tripId }
            BookingScreen(
                trip = trip,
                source = source,
                bookingName = uiState.bookingName,
                bookingEmail = uiState.bookingEmail,
                isBookingValid = uiState.isBookingValid,
                bookingResult = uiState.bookingResult,
                onNameChange = onBookingNameChange,
                onEmailChange = onBookingEmailChange,
                onSubmit = onSubmitBooking,
                onBack = {
                    onResetBooking()
                    appNavController.navigateUp()
                },
            )
        }
    }
}

