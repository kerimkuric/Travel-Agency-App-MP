package com.example.travelagencyapp.model.repository.mappers

import com.example.travelagencyapp.model.data.TripModel
import com.example.travelagencyapp.model.data.local.entity.TripWithDestinationAndCategories

fun TripWithDestinationAndCategories.toTripModel(): TripModel {
    val primaryCategory = categories.firstOrNull()?.name ?: "General"
    return TripModel(
        id = trip.id.toString(),
        title = trip.title,
        destination = destination.name,
        days = trip.days,
        pricePerPerson = trip.pricePerPerson,
        category = primaryCategory,
    )
}
