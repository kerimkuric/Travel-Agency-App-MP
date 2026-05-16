package com.example.travelagencyapp.model.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "destinations")
data class DestinationEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val country: String,
)
