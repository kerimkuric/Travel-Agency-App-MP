package com.example.travelagencyapp.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.travelagencyapp.presentation.navigation.AppNavGraph
import com.example.travelagencyapp.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelAgencyRoot(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val topLevelRoutes = setOf(Screen.Home.route, Screen.Trips.route, Screen.Search.route)
    val canGoBack = currentRoute !in topLevelRoutes

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Travel Agency App") },
                navigationIcon = {
                    if (canGoBack) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Text("<")
                        }
                    }
                },
            )
        },
    ) { innerPadding ->
        AppNavGraph(
            appNavController = navController,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
