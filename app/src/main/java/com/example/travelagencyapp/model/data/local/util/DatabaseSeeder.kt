package com.example.travelagencyapp.model.data.local.util

import com.example.travelagencyapp.model.data.local.dao.CategoryDao
import com.example.travelagencyapp.model.data.local.dao.DestinationDao
import com.example.travelagencyapp.model.data.local.dao.TripDao
import com.example.travelagencyapp.model.data.local.entity.CategoryEntity
import com.example.travelagencyapp.model.data.local.entity.DestinationEntity
import com.example.travelagencyapp.model.data.local.entity.TripCategoryCrossRef
import com.example.travelagencyapp.model.data.local.entity.TripEntity

object DatabaseSeeder {

    private val destinations = listOf(
        DestinationEntity(1, "Istanbul", "Turkey"),
        DestinationEntity(2, "Antalya", "Turkey"),
        DestinationEntity(3, "Paris", "France"),
        DestinationEntity(4, "Rome", "Italy"),
        DestinationEntity(5, "Barcelona", "Spain"),
    )

    private val categories = listOf(
        CategoryEntity(1, "City"),
        CategoryEntity(2, "Sea"),
        CategoryEntity(3, "Culture"),
        CategoryEntity(4, "Nature"),
        CategoryEntity(5, "Food"),
    )

    private val localTrips = listOf(
        TripEntity(1, "Istanbul Highlights", 1, 3, 450),
        TripEntity(2, "Antalya Coast Escape", 2, 5, 720),
        TripEntity(3, "Paris Weekend Break", 3, 4, 980),
        TripEntity(4, "Rome History Tour", 4, 4, 860),
        TripEntity(5, "Cappadocia Adventure", 1, 2, 390),
        TripEntity(6, "Barcelona Food Trip", 5, 3, 770),
    )

    private val localCategoryLinks = listOf(
        TripCategoryCrossRef(1, 1),
        TripCategoryCrossRef(1, 3),
        TripCategoryCrossRef(2, 2),
        TripCategoryCrossRef(3, 1),
        TripCategoryCrossRef(4, 3),
        TripCategoryCrossRef(5, 4),
        TripCategoryCrossRef(6, 5),
    )

    suspend fun seedIfEmpty(
        destinationDao: DestinationDao,
        categoryDao: CategoryDao,
        tripDao: TripDao,
    ) {
        destinationDao.insertAll(destinations)
        categoryDao.insertAll(categories)
        tripDao.insertAll(localTrips)
        tripDao.insertCategoryLinks(localCategoryLinks)
    }

    suspend fun seedDestinationsAndCategories(
        destinationDao: DestinationDao,
        categoryDao: CategoryDao,
    ) {
        destinationDao.insertAll(destinations)
        categoryDao.insertAll(categories)
    }

    suspend fun seedLocalTrips(tripDao: TripDao) {
        tripDao.insertAll(localTrips)
        tripDao.insertCategoryLinks(localCategoryLinks)
    }
}
