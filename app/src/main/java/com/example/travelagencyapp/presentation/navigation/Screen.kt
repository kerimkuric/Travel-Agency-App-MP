package com.example.travelagencyapp.presentation.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Trips : Screen("trips")
    data object Search : Screen("search")

    data object Details : Screen("details/{tripId}/{source}") {
        fun createRoute(tripId: String, source: String): String = "details/$tripId/$source"
    }

    data object Booking : Screen("booking/{tripId}/{source}") {
        fun createRoute(tripId: String, source: String): String = "booking/$tripId/$source"
    }
}

