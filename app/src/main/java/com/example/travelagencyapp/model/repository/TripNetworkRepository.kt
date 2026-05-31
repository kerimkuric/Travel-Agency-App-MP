package com.example.travelagencyapp.model.repository

import com.example.travelagencyapp.model.data.TripModel
import com.example.travelagencyapp.model.data.local.entity.TripCategoryCrossRef
import com.example.travelagencyapp.model.data.local.entity.TripEntity

data class RemoteTripBatch(
    val trips: List<TripEntity>,
    val categoryLinks: List<TripCategoryCrossRef>,
)

interface TripNetworkRepository {
    suspend fun fetchTripBatch(): RemoteTripBatch
    suspend fun fetchTrip(tripId: Long): TripModel
    suspend fun createTrip(trip: TripModel): TripModel
    suspend fun updateTrip(trip: TripModel): TripModel
    suspend fun deleteTrip(tripId: Long)
}
