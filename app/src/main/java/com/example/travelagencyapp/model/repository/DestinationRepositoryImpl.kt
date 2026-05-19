package com.example.travelagencyapp.model.repository

import com.example.travelagencyapp.model.data.local.dao.CategoryDao
import com.example.travelagencyapp.model.data.local.dao.DestinationDao
import com.example.travelagencyapp.model.data.local.dao.TripDao
import com.example.travelagencyapp.model.data.local.util.DatabaseSeeder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DestinationRepositoryImpl @Inject constructor(
    private val destinationDao: DestinationDao,
    private val categoryDao: CategoryDao,
    private val tripDao: TripDao,
) : DestinationRepository {

    private var seeded = false

    override suspend fun ensureSeeded() {
        if (seeded) return
        val existing = destinationDao.observeAll().first()
        if (existing.isEmpty()) {
            DatabaseSeeder.seedIfEmpty(destinationDao, categoryDao, tripDao)
        }
        seeded = true
    }

    override fun observeDestinationNames(): Flow<List<String>> {
        return destinationDao.observeAll().map { destinations ->
            listOf("All") + destinations.map { it.name }
        }
    }
}
