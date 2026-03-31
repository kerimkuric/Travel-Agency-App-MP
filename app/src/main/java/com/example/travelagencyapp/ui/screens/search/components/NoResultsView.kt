package com.example.travelagencyapp.ui.screens.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NoResultsView(
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text("No results found.")
        Text("Try a different keyword.")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onClear, modifier = Modifier.weight(1f)) {
                Text("Clear search")
            }
        }
    }
}

