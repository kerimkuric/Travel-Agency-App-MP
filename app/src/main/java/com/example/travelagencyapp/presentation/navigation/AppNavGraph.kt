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

@Composable
fun AppNavGraph(
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
                onOpenTrip = { tripId ->
                    appNavController.navigate(Screen.Details.createRoute(tripId, "home"))
                },
                onGoTrips = { appNavController.navigate(Screen.Trips.route) },
                onGoSearch = { appNavController.navigate(Screen.Search.route) },
            )
        }

        composable(Screen.Trips.route) {
            TripsScreen(
                onOpenTrip = { tripId ->
                    appNavController.navigate(Screen.Details.createRoute(tripId, "trips"))
                },
                onGoHome = { appNavController.navigateUp() },
                onGoSearch = { appNavController.navigate(Screen.Search.route) },
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(
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
            ),
        ) {
            DetailsScreen(
                onBack = { appNavController.navigateUp() },
                onBook = { tripId, source ->
                    appNavController.navigate(Screen.Booking.createRoute(tripId, source))
                },
            )
        }

        composable(
            route = Screen.Booking.route,
            arguments = listOf(
                navArgument("tripId") { type = NavType.StringType },
                navArgument("source") { type = NavType.StringType },
            ),
        ) {
            BookingScreen(
                onBack = { appNavController.navigateUp() },
            )
        }
    }
}
