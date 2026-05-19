package com.example.travelagencyapp.model.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "trips",
    foreignKeys = [
        ForeignKey(
            entity = DestinationEntity::class,
            parentColumns = ["id"],
            childColumns = ["destinationId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("destinationId")],
)
data class TripEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val destinationId: Long,
    val days: Int,
    val pricePerPerson: Int,
)
