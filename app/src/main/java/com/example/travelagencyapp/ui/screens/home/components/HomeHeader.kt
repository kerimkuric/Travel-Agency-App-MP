package com.example.travelagencyapp.ui.screens.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeHeader(
    title: String,
    onGoSearch: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
    ) {
        Text(text = title)
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))
        Button(onClick = onGoSearch) {
            Text("Search")
        }
    }
}

