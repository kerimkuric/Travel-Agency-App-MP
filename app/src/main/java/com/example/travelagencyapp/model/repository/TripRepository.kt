package com.example.travelagencyapp.model.repository

import com.example.travelagencyapp.model.data.TripModel
import kotlinx.coroutines.flow.Flow

interface TripRepository {
    suspend fun ensureSeeded()
    fun observeAllTrips(): Flow<List<TripModel>>
    fun observeTrip(tripId: Long): Flow<TripModel?>
    suspend fun deleteTrip(tripId: Long)
}
