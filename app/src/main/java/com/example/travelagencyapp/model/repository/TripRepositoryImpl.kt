package com.example.travelagencyapp.model.repository

import com.example.travelagencyapp.model.data.TripModel
import com.example.travelagencyapp.model.data.local.dao.DestinationDao
import com.example.travelagencyapp.model.data.local.dao.TripDao
import com.example.travelagencyapp.model.data.local.util.DatabaseSeeder
import com.example.travelagencyapp.model.repository.mappers.toTripModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripRepositoryImpl @Inject constructor(
    private val tripDao: TripDao,
    private val destinationDao: DestinationDao,
    private val categoryDao: com.example.travelagencyapp.model.data.local.dao.CategoryDao,
) : TripRepository {

    private var seeded = false

    override suspend fun ensureSeeded() {
        if (seeded) return
        val existing = tripDao.observeTripsWithRelations().first()
        if (existing.isEmpty()) {
            DatabaseSeeder.seedIfEmpty(destinationDao, categoryDao, tripDao)
        }
        seeded = true
    }

    override fun observeAllTrips(): Flow<List<TripModel>> {
        return tripDao.observeTripsWithRelations().map { list ->
            list.map { it.toTripModel() }
        }
    }

    override fun observeTrip(tripId: Long): Flow<TripModel?> {
        return tripDao.observeTripWithRelations(tripId).map { relation ->
            relation?.toTripModel()
        }
    }

    override suspend fun deleteTrip(tripId: Long) {
        tripDao.deleteById(tripId)
    }
}
