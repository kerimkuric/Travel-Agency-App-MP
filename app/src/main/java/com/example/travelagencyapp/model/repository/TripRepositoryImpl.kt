package com.example.travelagencyapp.model.repository

import com.example.travelagencyapp.model.data.TripModel
import com.example.travelagencyapp.model.data.local.dao.CategoryDao
import com.example.travelagencyapp.model.data.local.dao.DestinationDao
import com.example.travelagencyapp.model.data.local.dao.TripDao
import com.example.travelagencyapp.model.data.local.entity.TripCategoryCrossRef
import com.example.travelagencyapp.model.data.local.entity.TripEntity
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
    private val categoryDao: CategoryDao,
    private val tripNetworkRepository: TripNetworkRepository,
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

    override suspend fun ensureLocalCatalog() {
        ensureDestinationsAndCategories()
        tripDao.deleteAllCategoryLinks()
        tripDao.deleteAllTrips()
        DatabaseSeeder.seedLocalTrips(tripDao)
        seeded = true
    }

    override suspend fun syncTripsFromNetwork() {
        ensureDestinationsAndCategories()
        val batch = tripNetworkRepository.fetchTripBatch()
        tripDao.deleteAllCategoryLinks()
        tripDao.deleteAllTrips()
        tripDao.insertAll(batch.trips)
        tripDao.insertCategoryLinks(batch.categoryLinks)
        seeded = true
    }

    override suspend fun createTripOnNetwork(trip: TripModel): TripModel {
        ensureDestinationsAndCategories()
        val created = tripNetworkRepository.createTrip(trip)
        val entity = created.let { remote ->
            TripEntity(
                id = remote.id.toLong(),
                title = remote.title,
                destinationId = destinationIdForName(remote.destination),
                days = remote.days,
                pricePerPerson = remote.pricePerPerson,
            )
        }
        tripDao.insertAll(listOf(entity))
        tripDao.insertCategoryLinks(
            listOf(
                TripCategoryCrossRef(
                    tripId = entity.id,
                    categoryId = categoryIdForName(created.category),
                ),
            ),
        )
        return created
    }

    override suspend fun updateTripOnNetwork(trip: TripModel): TripModel {
        val updated = tripNetworkRepository.updateTrip(trip)
        val entity = TripEntity(
            id = updated.id.toLong(),
            title = updated.title,
            destinationId = destinationIdForName(updated.destination),
            days = updated.days,
            pricePerPerson = updated.pricePerPerson,
        )
        tripDao.insertAll(listOf(entity))
        tripDao.insertCategoryLinks(
            listOf(
                TripCategoryCrossRef(
                    tripId = entity.id,
                    categoryId = categoryIdForName(updated.category),
                ),
            ),
        )
        return updated
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
        tripNetworkRepository.deleteTrip(tripId)
        tripDao.deleteById(tripId)
    }

    private suspend fun ensureDestinationsAndCategories() {
        val destinations = destinationDao.observeAll().first()
        if (destinations.isEmpty()) {
            DatabaseSeeder.seedDestinationsAndCategories(destinationDao, categoryDao)
        }
    }

    private fun destinationIdForName(name: String): Long {
        return when (name) {
            "Istanbul" -> 1L
            "Antalya" -> 2L
            "Paris" -> 3L
            "Rome" -> 4L
            "Barcelona" -> 5L
            else -> 1L
        }
    }

    private fun categoryIdForName(name: String): Long {
        return when (name) {
            "City" -> 1L
            "Sea" -> 2L
            "Culture" -> 3L
            "Nature" -> 4L
            "Food" -> 5L
            else -> 1L
        }
    }
}
