package com.example.travelagencyapp.model.data.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TripWithDestinationAndCategories(
    @Embedded val trip: TripEntity,
    @Relation(
        parentColumn = "destinationId",
        entityColumn = "id",
    )
    val destination: DestinationEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = TripCategoryCrossRef::class,
            parentColumn = "tripId",
            entityColumn = "categoryId",
        ),
    )
    val categories: List<CategoryEntity>,
)
