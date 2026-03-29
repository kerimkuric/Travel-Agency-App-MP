package com.example.travelagencyapp.ui.screens.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.travelagencyapp.R
import com.example.travelagencyapp.model.Trip

@Composable
fun TripSearchResultItem(
    trip: Trip,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Trip image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
            )
            Text(text = trip.title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp))
            Text(text = trip.destination)
            Text(text = "${trip.days} days - $${trip.pricePerPerson}/person")
        }
    }
}

