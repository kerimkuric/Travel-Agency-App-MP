package com.example.travelagencyapp.model.repository

import com.example.travelagencyapp.model.data.TripModel
import kotlinx.coroutines.flow.Flow

interface TripRepository {
    suspend fun ensureSeeded()
    /** Restores the built-in trip catalog (used by Home / Search). */
    suspend fun ensureLocalCatalog()
    suspend fun syncTripsFromNetwork()
    suspend fun createTripOnNetwork(trip: TripModel): TripModel
    suspend fun updateTripOnNetwork(trip: TripModel): TripModel
    fun observeAllTrips(): Flow<List<TripModel>>
    fun observeTrip(tripId: Long): Flow<TripModel?>
    suspend fun deleteTrip(tripId: Long)
}
