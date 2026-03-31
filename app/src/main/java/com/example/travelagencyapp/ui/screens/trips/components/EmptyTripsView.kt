package com.example.travelagencyapp.ui.screens.trips.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmptyTripsView(
    onReset: () -> Unit,
    onGoHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text("No trips yet.")
        Text("Use \"Restore sample\" to see example trips.")

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onReset, modifier = Modifier.weight(1f)) {
                Text("Restore sample")
            }
            Button(onClick = onGoHome, modifier = Modifier.weight(1f)) {
                Text("Home")
            }
        }
    }
}

