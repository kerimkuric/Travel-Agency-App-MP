package com.example.travelagencyapp.model.data.remote.mappers

import com.example.travelagencyapp.model.data.TripModel
import com.example.travelagencyapp.model.data.local.entity.TripCategoryCrossRef
import com.example.travelagencyapp.model.data.local.entity.TripEntity
import com.example.travelagencyapp.model.data.remote.dto.TripRemoteDto

private val destinationNames = listOf("Istanbul", "Antalya", "Paris", "Rome", "Barcelona")
private val categoryNames = listOf("City", "Sea", "Culture", "Nature", "Food")

/** Real trip names shown in the app — API titles are placeholder Latin and are not shown. */
private val curatedTripTitles = listOf(
    "Istanbul Highlights",
    "Antalya Coast Escape",
    "Paris Weekend Break",
    "Rome History Tour",
    "Cappadocia Adventure",
    "Barcelona Food Trip",
    "Mediterranean Discovery",
    "Old Town & Bazaars",
    "Riviera Sun Package",
    "Heritage City Walk",
    "Coastal Retreat",
    "Romantic Getaway",
    "Food & Culture Trail",
    "Scenic Explorer",
    "Weekend City Break",
)

private fun TripRemoteDto.displayTitle(): String {
    val tripId = requireNotNull(id).toLong()
    return curatedTripTitles[((tripId - 1) % curatedTripTitles.size).toInt()]
}

fun TripRemoteDto.toTripEntity(): TripEntity {
    val tripId = requireNotNull(id) { "Remote trip id is required" }.toLong()
    val destinationId = destinationIdFromIndex(destinationIndex)
    return TripEntity(
        id = tripId,
        title = displayTitle(),
        destinationId = destinationId,
        days = 3 + (tripId % 5).toInt(),
        pricePerPerson = (350 + tripId * 35).toInt(),
    )
}

fun TripRemoteDto.toCategoryLink(): TripCategoryCrossRef {
    val tripId = requireNotNull(id).toLong()
    val categoryId = ((destinationIndex - 1).coerceAtLeast(0) % 5 + 1).toLong()
    return TripCategoryCrossRef(tripId = tripId, categoryId = categoryId)
}

fun TripRemoteDto.toTripModel(): TripModel {
    val tripId = requireNotNull(id).toLong()
    val destination = destinationNames[((destinationIndex - 1).coerceAtLeast(0) % destinationNames.size)]
    val category = categoryNames[((destinationIndex - 1).coerceAtLeast(0) % categoryNames.size)]
    return TripModel(
        id = tripId.toString(),
        title = displayTitle(),
        destination = destination,
        days = 3 + (tripId % 5).toInt(),
        pricePerPerson = (350 + tripId * 35).toInt(),
        category = category,
    )
}

fun TripModel.toRemoteDto(): TripRemoteDto {
    val destinationIndex = destinationNames.indexOf(destination).let { index ->
        if (index >= 0) index + 1 else 1
    }
    return TripRemoteDto(
        id = id.toIntOrNull(),
        destinationIndex = destinationIndex,
        title = title,
        body = "Package: $destination · $days days · €$pricePerPerson · $category",
    )
}

private fun destinationIdFromIndex(destinationIndex: Int): Long {
    return ((destinationIndex - 1).coerceAtLeast(0) % 5 + 1).toLong()
}
